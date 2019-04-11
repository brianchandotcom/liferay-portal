import Component from 'metal-jsx';
import {Config} from 'metal-state';
import {convertToSearchParams, makeFetch} from '../../util/fetch.es';
import {debounce} from 'metal-debounce';
import {PagesVisitor} from '../../util/visitors.es';

const WithEvaluator = ChildComponent => {

	/**
	 * FormRenderer.
	 * @extends Component
	 */

	class Evaluator extends Component {
		static PROPS = {
			editingLanguageId: Config.string(),

			/**
			 * @instance
			 * @memberof Evaluator
			 * @type {string}
			 * @required
			 */

			fieldType: Config.string().required(),

			/**
			 * @instance
			 * @memberof Evaluator
			 * @type {object}
			 * @required
			 */

			formContext: Config.object().required(),

			url: Config.string()
		}

		static STATE = {
			pages: Config.array().valueFn('_pagesValueFn')
		}

		attached() {
			this.evaluate();
		}

		created() {
			this.evaluate = debounce(this.evaluate.bind(this), 300);
		}

		willReceiveProps(props) {
			const {formContext} = props;

			if (formContext && Object.keys(formContext.newVal).length) {
				this.setState(
					{
						pages: formContext.newVal.pages
					}
				);
			}
		}

		/**
		 * @param {!Object} event
		 * @private
		 */

		_handleFieldEdited(event) {
			const {fieldInstance} = event;
			const {evaluable} = fieldInstance;

			if (evaluable) {
				this.evaluate(fieldInstance);
			}

			this.emit('fieldEdited', event);
		}

		_mergeFieldOptions(field, newField) {
			let newValue = {...newField.value};

			for (const languageId in newValue) {
				newValue = {
					...newValue,
					[languageId]: newValue[languageId].map(
						option => {
							const existingOption = field.value[languageId]
								.find(
									({value}) => value === option.value
								);

							return {
								...option,
								edited: (
									existingOption &&
									existingOption.edited
								)
							};
						}
					)
				};
			}

			return newValue;
		}

		/**
		 * Merges fields from two array of pages. This is used to get new
		 * information from the evaluator and update fields with it while
		 * maintaining properties that were not changed by the evaluation
		 * process.
		 * @private
		 */

		_mergePages(sourcePages, newPages) {
			const {defaultLanguageId, editingLanguageId} = this.props;
			const visitor = new PagesVisitor(sourcePages);

			return visitor.mapFields(
				(field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
					let newField = {
						...field,
						...newPages[pageIndex].rows[rowIndex].columns[columnIndex].fields[fieldIndex],
						defaultLanguageId,
						editingLanguageId
					};

					if (newField.fieldName === 'name') {
						newField = {
							...newField,
							visible: true
						};
					}

					if (newField.type === 'options') {
						newField = {
							...newField,
							value: this._mergeFieldOptions(field, newField)
						};
					}

					if (field.localizable) {
						newField = {
							...newField,
							localizedValue: {
								...field.localizedValue,
								...(newField.localizedValue || {})
							}
						};
					}

					return newField;
				}
			);
		}

		/**
		 * @private
		 */

		_pagesValueFn() {
			const {formContext} = this.props;

			return formContext.pages;
		}

		evaluate(fieldInstance) {
			if (!this.isDisposed()) {
				const {
					editingLanguageId,
					formContext,
					url
				} = this.props;
				const {pages} = this.state;

				let fieldName;

				if (fieldInstance && !fieldInstance.isDisposed()) {
					fieldName = fieldInstance.fieldName;
				}

				makeFetch(
					{
						body: convertToSearchParams(
							{
								languageId: editingLanguageId,
								p_auth: Liferay.authToken,
								serializedFormContext: JSON.stringify(formContext),
								trigger: fieldName
							}
						),
						url
					}
				).then(
					newPages => {
						const mergedPages = this._mergePages(pages, newPages);

						if (!this.isDisposed()) {
							this.emit('evaluated', mergedPages);
						}
					}
				);
			}
		}

		/**
		 * Render the call of it's children
		 * @return {function}
		 */

		render() {
			const {pages} = this.state;
			const {editingLanguageId, events} = this.props;

			return (
				<ChildComponent
					{...this.props}
					editingLanguageId={editingLanguageId}
					events={{
						...events,
						fieldEdited: this._handleFieldEdited.bind(this)
					}}
					pages={pages}
				/>
			);
		}
	}

	return Evaluator;
};

export default WithEvaluator;