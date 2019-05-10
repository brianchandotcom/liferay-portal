import Component, {Config} from 'metal-jsx';
import core from 'metal';
import FormBuilder from 'dynamic-data-mapping-form-builder/js/components/FormBuilder/FormBuilder.es';
import LayoutProvider from 'dynamic-data-mapping-form-builder/js/components/LayoutProvider/LayoutProvider.es';
import {pageStructure} from 'dynamic-data-mapping-form-builder/js/util/config.es';
import {PagesVisitor} from 'dynamic-data-mapping-form-builder/js/util/visitors.es';

/**
 * Layout Builder.
 * @extends Component
 */
class LayoutBuilder extends Component {

	attached() {
		const dependencies = [this._getTranslationManager()];

		Promise.all(dependencies).then(
			results => {
				const translationManager = results[0];

				if (translationManager) {
					translationManager.on(
						'editingLocaleChange',
						event => {
							this.props.editingLanguageId = event.newVal;
						}
					);

					translationManager.on(
						'deleteAvailableLocale',
						event => {
							layoutProvider.emit('languageIdDeleted', event);
						}
					);
				}
			}
		);
	}

	render() {
		const {
			context,
			defaultLanguageId,
			editingLanguageId,
			fieldTypes,
			namespace,
			spritemap
		} = this.props;

		const layoutProviderProps = {
			...this.props,
			defaultLanguageId,
			editingLanguageId,
			events: {
				pagesChanged: this._handlePagesChanged.bind(this)
			},
			initialPages: context.pages,
			initialPaginationMode: context.paginationMode,
			ref: 'layoutProvider'
		};

		return (
			<div class={'ddm-form-builder'}>
				<LayoutProvider {...layoutProviderProps}>
					<FormBuilder
						defaultLanguageId={defaultLanguageId}
						editingLanguageId={editingLanguageId}
						fieldTypes={fieldTypes}
						namespace={namespace}
						paginationMode={'wizard'}
						ref="builder"
						spritemap={spritemap}
					/>
				</LayoutProvider>
			</div>
		);
	}

	_handlePagesChanged({newVal}) {
		const {dataLayoutInputId} = this.props;

		if (dataLayoutInputId) {
			const dataLayoutInput = document.querySelector(`#${dataLayoutInputId}`);

			dataLayoutInput.value = this._serializeDataLayout(newVal);
		}
	}

	_getTranslationManager() {
		let promise;

		const translationManager = Liferay.component('translationManager');

		if (translationManager) {
			promise = Promise.resolve(translationManager);
		}
		else {
			promise = Liferay.componentReady('translationManager');
		}

		return promise;
	}

	_serializeDataLayout(pages) {
		const pagesVisitor = new PagesVisitor(pages);

		const newPages = pagesVisitor.mapFields(
			({fieldName, settingsContext}) => {
				const columnConfig = {};
				const settingsContextVisitor = new PagesVisitor(settingsContext.pages);

				settingsContextVisitor.mapFields(
					({fieldName, localizable, localizedValue, value}) => {
						if (localizable) {
							columnConfig[fieldName] = localizedValue;
						}
						else {
							if (fieldName === 'dataType') {
								fieldName = 'type';
							}

							columnConfig[fieldName] = value;
						}
					}
				);

				return {
					...columnConfig,
					fieldName
				};
			}
		);

		return JSON.stringify(newPages);
	}

	_setContext(context) {
		const {defaultLanguageId} = this.props;

		const emptyLocalizableValue = {
			[defaultLanguageId]: ''
		};

		const pages = context.pages || [];

		if (!pages.length) {
			context = {
				...context,
				pages: [
					{
						description: '',
						localizedDescription: emptyLocalizableValue,
						localizedTitle: emptyLocalizableValue,
						rows: [
							{
								columns: [
									{
										fields: [],
										size: 12
									}
								]
							}
						],
						title: ''
					}
				],
				paginationMode: 'wizard',
				rules: context.rules || []
			};
		}

		return {
			...context,
			pages: context.pages.map(
				page => {
					let {description, localizedDescription, localizedTitle, title} = page;

					if (!core.isString(description)) {
						description = description[languageId];
						localizedDescription = {
							[languageId]: description
						};
					}

					if (!core.isString(title)) {
						title = title[languageId];
						localizedTitle = {
							[languageId]: title
						};
					}

					return {
						...page,
						description,
						localizedDescription,
						localizedTitle,
						title
					};
				}
			)
		};
	}
}

LayoutBuilder.PROPS = {
	context: Config.shapeOf(
		{
			pages: Config.arrayOf(pageStructure),
			paginationMode: Config.string(),
			rules: Config.array()
		}
	).required().setter('_setContext'),

	/**
	 * The target input for the serialized data layout.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	dataLayoutInputId: Config.string(),


	editingLanguageId: Config.string().value(themeDisplay.getDefaultLanguageId()),

	/**
	 * The available field types to display on the side bar.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	fieldTypes: Config.array().value([]),

	/**
	 * The default language id of the form.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!array}
	 */

	defaultLanguageId: Config.string().value(themeDisplay.getDefaultLanguageId()),

	/**
	 * The namespace of the portlet.
	 * @default undefined
	 * @instance
	 * @memberof FormBuilderTagLib
	 * @type {!string}
	 */

	namespace: Config.string().required(),

	/**
	 * The path to the SVG spritemap file containing the icons.
	 * @default undefined
	 * @instance
	 * @memberof Form
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

export default LayoutBuilder;
export {LayoutBuilder};