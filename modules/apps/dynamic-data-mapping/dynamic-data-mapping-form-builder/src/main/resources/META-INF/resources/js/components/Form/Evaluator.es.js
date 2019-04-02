import autobind from 'autobind-decorator';
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

		created() {
			this._processEvaluation = debounce(this._processEvaluation.bind(this), 300);
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

		@autobind
		_handleFieldEdited(event) {
			const {fieldInstance} = event;
			const {evaluable} = fieldInstance;

			if (evaluable) {
				this._processEvaluation(fieldInstance);
			}

			this.emit('fieldEdited', event);
		}

		/**
		 * Merges fields from two array of pages. This is used to get new information from the evaluator
		 * and update fields with it while maintaining properties that were not changed by the evaluation process.
		 * @private
		 */

		_mergePages(sourcePages, newPages) {
			const visitor = new PagesVisitor(sourcePages);

			const settingsContext = visitor.mapFields(
				(field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
					const currentField = newPages[pageIndex].rows[rowIndex].columns[columnIndex].fields[fieldIndex];

					if (currentField.fieldName === 'name') {
						currentField.visible = true;
					}

					return currentField;
				}
			);

			return settingsContext;
		}

		/**
		 * @private
		 */

		_pagesValueFn() {
			const {formContext} = this.props;

			return formContext.pages;
		}

		/**
		 * @private
		 */

		_processEvaluation(fieldInstance) {
			if (!fieldInstance.isDisposed() && !this.isDisposed()) {
				const {fieldName} = fieldInstance;
				const {fieldType, formContext, url} = this.props;
				const {pages} = this.state;

				makeFetch(
					{
						body: convertToSearchParams(
							{
								languageId: themeDisplay.getLanguageId(),
								newField: '',
								p_auth: Liferay.authToken,
								portletNamespace: '',
								serializedFormContext: JSON.stringify(formContext),
								trigger: fieldName,
								type: fieldType
							}
						),
						url
					}
				).then(
					newPages => {
						const mergedPages = this._mergePages(pages, newPages);

						this.emit('evaluated', mergedPages);

						this.setState(
							{
								pages: mergedPages
							}
						);
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
						fieldEdited: this._handleFieldEdited
					}}
					pages={pages}
				/>
			);
		}
	}

	return Evaluator;
};

export default WithEvaluator;