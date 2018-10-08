import {Config} from 'metal-state';
import {debounce} from 'metal-debounce';
import {makeFetch} from '../../util/fetch.es';
import {PagesVisitor} from '../../util/visitors.es';
import autobind from 'autobind-decorator';
import Component from 'metal-jsx';

const WithEvaluator = ChildComponent => {

	/**
	 * FormRenderer.
	 * @extends Component
	 */

	class Evaluator extends Component {
		static PROPS = {

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

		_mergePages(sourcePages, targetPages) {
			const visitor = new PagesVisitor(sourcePages);

			return visitor.mapFields(
				(field, fieldIndex, columnIndex, rowIndex, pageIndex) => {
					const currentField = targetPages[pageIndex].rows[rowIndex].columns[columnIndex].fields[fieldIndex];

					if (currentField.fieldName === 'name') {
						currentField.visible = true;
					}

					return {
						...field,
						errorMessage: currentField.errorMessage,
						options: currentField.options,
						readOnly: currentField.readOnly,
						required: currentField.required,
						valid: currentField.valid,
						visible: currentField.visible
					};
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

		/**
		 * @private
		 */

		_processEvaluation({fieldName}) {
			const {fieldType, formContext, url} = this.props;
			const {pages} = this.state;

			makeFetch(
				{
					body: {
						languageId: themeDisplay.getLanguageId(),
						newField: '',
						p_auth: Liferay.authToken,
						portletNamespace: '',
						serializedFormContext: JSON.stringify(formContext),
						trigger: fieldName,
						type: fieldType
					},
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

		/**
		 * Render the call of it's children
		 * @return {function}
		 */

		render() {
			const {pages} = this.state;
			const events = {
				fieldEdited: this._handleFieldEdited
			};

			return (
				<ChildComponent
					{...this.props}
					events={events}
					pages={pages}
				/>
			);
		}
	}

	return Evaluator;
};

export default WithEvaluator;