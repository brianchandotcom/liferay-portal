import './EditablePageHeader.soy.js';
import Component, {Fragment} from 'metal-jsx';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import {focusedFieldStructure, pageStructure, ruleStructure} from '../../util/config.es';
import {PagesVisitor} from '../../util/visitors.es';
import {sub} from '../../util/strings.es.js';

const withEditablePageHeader = ChildComponent => {
	class EditablePageHeader extends Component {
		static PROPS = {

			/**
			 * @default
			 * @instance
			 * @memberof FormBuilder
			 * @type {?number}
			 */

			activePage: Config.number().value(0),

			/**
			 * @default undefined
			 * @instance
			 * @memberof FormBuilder
			 * @type {?string}
			 */

			defaultLanguageId: Config.string(),

			/**
			 * @default undefined
			 * @instance
			 * @memberof FormBuilder
			 * @type {?string}
			 */

			editingLanguageId: Config.string(),

			/**
			 * @default []
			 * @instance
			 * @memberof Sidebar
			 * @type {?(array|undefined)}
			 */

			fieldTypes: Config.array().value([]),

			/**
			 * @default {}
			 * @instance
			 * @memberof FormBuilder
			 * @type {?object}
			 */

			focusedField: focusedFieldStructure.value({}),

			/**
			 * @default []
			 * @instance
			 * @memberof FormBuilder
			 * @type {?array<object>}
			 */

			pages: Config.arrayOf(pageStructure).value([]),

			/**
			 * @instance
			 * @memberof FormBuilder
			 * @type {string}
			 */

			paginationMode: Config.string().required(),

			/**
			 * @instance
			 * @memberof FormBuilder
			 * @type {string}
			 */

			rules: Config.arrayOf(ruleStructure).required(),

			/**
			 * @default undefined
			 * @instance
			 * @memberof FormRenderer
			 * @type {!string}
			 */

			spritemap: Config.string().required()
		}

		attached() {
			this._eventHandler = new EventHandler();

			this._eventHandler.add(
				this.delegate('input', '.form-builder-page-header-title', this._handlePageTitleChanged.bind(this)),
				this.delegate('input', '.form-builder-page-header-description', this._handlePageDescriptionChanged.bind(this))
			);
		}

		disposed() {
			this._eventHandler.removeAllListeners();
		}

		getPages() {
			const {pages} = this.props;
			const total = pages.length;
			const visitor = new PagesVisitor(pages);

			return visitor.mapPages(
				(page, pageIndex) => {
					return {
						...page,
						headerRenderer: 'editable',
						pageIndex,
						placeholder: sub(Liferay.Language.get('untitled-page-x-of-x'), [pageIndex + 1, total]),
						total
					};
				}
			);
		}

		render() {
			return (
				<Fragment>
					<ChildComponent {...this.props} pages={this.getPages()} />
				</Fragment>
			);
		}

		_handlePageDescriptionChanged(event) {
			const {activePage, editingLanguageId, pages} = this.props;
			const {delegateTarget} = event;
			const {dispatch} = this.context;
			const value = delegateTarget.value;
			const visitor = new PagesVisitor(pages);

			dispatch(
				'pagesUpdated',
				visitor.mapPages(
					(page, pageIndex) => {
						if (pageIndex === activePage) {
							page = {
								...page,
								description: value,
								localizedDescription: {
									...page.localizedDescription,
									[editingLanguageId]: value
								}
							};
						}

						return page;
					}
				)
			);
		}

		_handlePageTitleChanged(event) {
			const {activePage, editingLanguageId, pages} = this.props;
			const {delegateTarget} = event;
			const {dispatch} = this.context;
			const value = delegateTarget.value;
			const visitor = new PagesVisitor(pages);

			dispatch(
				'pagesUpdated',
				visitor.mapPages(
					(page, pageIndex) => {
						if (pageIndex === activePage) {
							page = {
								...page,
								localizedTitle: {
									...page.localizedTitle,
									[editingLanguageId]: value
								},
								title: value
							};
						}

						return page;
					}
				)
			);
		}
	}

	return EditablePageHeader;
};

export default withEditablePageHeader;