import Component, {Config} from 'metal-jsx';
import core from 'metal';
import FormBuilder from 'dynamic-data-mapping-form-builder/js/components/FormBuilder/FormBuilder.es';
import LayoutProvider from 'dynamic-data-mapping-form-builder/js/components/LayoutProvider/LayoutProvider.es';
import {pageStructure} from 'dynamic-data-mapping-form-builder/js/util/config.es';
import {PagesVisitor} from 'dynamic-data-mapping-form-renderer/js/metal/util/visitors.es';

/**
 * Data Layout Builder.
 * @extends Component
 */
class DataLayoutBuilder extends Component {

	attached() {
		const {layoutProvider} = this.refs;
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
			portletNamespace,
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
						paginationMode={'wizard'}
						portletNamespace={portletNamespace}
						ref="builder"
						spritemap={spritemap}
					/>
				</LayoutProvider>
			</div>
		);
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

	_handlePagesChanged({newVal}) {
		const {dataDefinitionInputId, dataLayoutInputId} = this.props;

		if (dataDefinitionInputId && dataLayoutInputId) {
			const dataDefinitionInput = document.querySelector(`#${dataDefinitionInputId}`);
			const dataLayoutInput = document.querySelector(`#${dataLayoutInputId}`);

			const data = this._serialize(newVal);

			dataLayoutInput.value = data.layout;
			dataDefinitionInput.value = data.definition;
		}
	}

	_serialize(pages) {
		const {defaultLanguageId} = this.props;
		const columnDefinitions = [];
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

				columnDefinitions.push(columnConfig);

				return fieldName;
			}
		);

		return {
			definition: JSON.stringify(
				{
					fields: columnDefinitions
				}
			),
			layout: JSON.stringify(
				{
					defaultLanguageId,
					pages: newPages,
					paginationMode: 'wizard'
				}
			)
		};
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
						description = description[defaultLanguageId];
						localizedDescription = {
							[defaultLanguageId]: description
						};
					}

					if (!core.isString(title)) {
						title = title[defaultLanguageId];
						localizedTitle = {
							[defaultLanguageId]: title
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

DataLayoutBuilder.PROPS = {
	context: Config.shapeOf(
		{
			pages: Config.arrayOf(pageStructure),
			paginationMode: Config.string(),
			rules: Config.array()
		}
	).required().setter('_setContext'),
	dataDefinitionInputId: Config.string(),
	dataLayoutInputId: Config.string(),
	defaultLanguageId: Config.string().value(themeDisplay.getDefaultLanguageId()),
	editingLanguageId: Config.string().value(themeDisplay.getDefaultLanguageId()),
	fieldTypes: Config.array().value([]),
	portletNamespace: Config.string().required(),
	spritemap: Config.string().required()
};

export default DataLayoutBuilder;
export {DataLayoutBuilder};