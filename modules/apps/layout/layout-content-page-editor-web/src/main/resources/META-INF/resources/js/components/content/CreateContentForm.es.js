/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {dom} from 'metal-dom';
import {Config} from 'metal-state';
import {PortletBase} from 'frontend-js-web';
import Soy from 'metal-soy';

import getConnectedComponent from '../../store/ConnectedComponent.es';
import templates from './CreateContentForm.soy';

/**
 * CreateContentForm
 */
class CreateContentForm extends PortletBase {
	/**
	 * @inheritdoc
	 * @review
	 */
	syncGetContentStructuresURL() {
		if (this.getContentStructuresURL) {
			this._structures = null;

			this.fetch(this.getContentStructuresURL)
				.then(response => response.json())
				.then(response => {
					this._structures = response;

					if (this._structures.length > 0) {
						this.emit('structureChanged', {
							ddmStructure: {
								id: this._structures[0].id,
								label: this._structures[0].label
							}
						});
					}
				});
		}
	}

	_handleContentNameChange() {
		this._validateForm();

		this.emit('titleChanged', {
			title: this.refs.contentName.value
		});
	}

	_handleStructureChange(event) {
		const targetElement = event.delegateTarget;

		this.emit('structureChanged', {
			ddmStructure: {
				id: targetElement.options[targetElement.selectedIndex].value,
				label: targetElement.options[targetElement.selectedIndex].text
			}
		});
	}

	_validateForm() {
		if (this.refs.contentName.value === '') {
			dom.addClasses(this.refs.contentName.parentNode, 'has-error');
			dom.removeClasses(this.refs.contentName.nextSibling, 'hide');

			this.emit('formValidated', {
				valid: false
			});
		} else {
			dom.removeClasses(this.refs.contentName.parentNode, 'has-error');
			dom.addClasses(this.refs.contentName.nextSibling, 'hide');

			this.emit('formValidated', {
				valid: true
			});
		}
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
CreateContentForm.STATE = {
	/**
	 * List of available structure types
	 * @default null
	 * @instance
	 * @memberOf CreateContentForm
	 * @private
	 * @review
	 * @type {Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */
	_structureTypes: Config.arrayOf(
		Config.shapeOf({
			id: Config.string().required(),
			label: Config.string().required()
		})
	)
		.internal()
		.value([
			{
				id: 'existing',
				label: Liferay.Language.get('existing-structure')
			}
		]),

	/**
	 * List of available structures
	 * @default null
	 * @instance
	 * @memberOf CreateContentForm
	 * @private
	 * @review
	 * @type {Array<{
	 *   id: !string,
	 *   label: !string
	 * }>}
	 */
	_structures: Config.arrayOf(
		Config.shapeOf({
			id: Config.string().required(),
			label: Config.string().required()
		})
	)
		.internal()
		.value(null),

	/**
	 * Selected structure ID
	 * @default null
	 * @instance
	 * @memberOf CreateContentForm
	 * @private
	 * @review
	 * @type {string}
	 */
	selectedStructureId: Config.string().value('')
};

const ConnectedCreateContentForm = getConnectedComponent(CreateContentForm, [
	'getContentStructuresURL',
	'portletNamespace'
]);

Soy.register(ConnectedCreateContentForm, templates);

export {ConnectedCreateContentForm, CreateContentForm};
export default ConnectedCreateContentForm;
