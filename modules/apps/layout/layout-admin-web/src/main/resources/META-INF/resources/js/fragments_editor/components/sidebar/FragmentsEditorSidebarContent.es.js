import Component from 'metal-component';
import {Config} from 'metal-state';
import Soy from 'metal-soy';

import './fragments/SidebarElementsPanel.es';
import './fragments/SidebarSectionsPanel.es';
import './layouts/SidebarLayoutsPanel.es';
import './mapping/SidebarMappingPanel.es';
import './structure/SidebarStructurePanel.es';
import templates from './FragmentsEditorSidebarContent.soy';

/**
 * FragmentsEditorSidebarContent
 * @review
 */
class FragmentsEditorSidebarContent extends Component {

	/**
	 * Updates active panel
	 * @param {!MouseEvent} event
	 * @private
	 * @review
	 */
	_handlePanelButtonClick(event) {
		this._panelId = event.delegateTarget.dataset.panelId;

		this.emit('sidebarTitleChanged', event);
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FragmentsEditorSidebarContent.STATE = {

	/**
	 * Sidebar panels
	 * @default []
	 * @memberof FragmentsEditorSidebarContent
	 * @private
	 * @review
	 * @type {object}
	 */
	_panels: Config
		.arrayOf(
			Config.shapeOf(
				{
					icon: Config.string(),
					label: Config.string(),
					panelId: Config.string()
				}
			)
		)
		.internal()
		.value(
			[
				{
					icon: 'cards',
					label: 'Sections',
					panelId: 'sections'
				},
				{
					icon: 'cards',
					label: 'Elements',
					panelId: 'elements'
				},
				{
					icon: 'page-template',
					label: 'Layouts',
					panelId: 'layouts'
				},
				{
					icon: 'simulation-menu',
					label: 'Mapping',
					panelId: 'mapping'
				},
				{
					icon: 'pages-tree',
					label: 'Structure',
					panelId: 'structure'
				}
			]
		),

	/**
	 * Sidebar active panel ID
	 * @default sections
	 * @memberof FragmentsEditorSidebarContent
	 * @private
	 * @review
	 * @type {string}
	 */
	_panelId: Config
		.string()
		.internal()
		.value('sections')
};

Soy.register(FragmentsEditorSidebarContent, templates);

export {FragmentsEditorSidebarContent};
export default FragmentsEditorSidebarContent;