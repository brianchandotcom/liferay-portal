import 'clay-sticker';
import 'clay-select';
import Component from 'metal-component';
import dom from 'metal-dom';
import Soy from 'metal-soy';
import templates from './ManageCollaborators.soy';
import {Config} from 'metal-state';


class ManageCollaborators extends Component {
	attached() {
		this._deletedCollaborators = [];
	}

	_handleSaveButtonClick() {
		console.log('fetch: '+ this.uri);

		fetch(
			this.uri,
			{
				credentials: 'include',
				method: 'POST',
				headers: {
			    	Accept: 'application/json'
				}
			}
		)
		.then(
			response => {
				console.log('OK');
				console.log(response);
			}
		)
		.catch(
			err => {debugger}
		)
	}

	_handleDeleteCollaborator(event) {
		let collaboratorId = event.delegateTarget.dataset.collaboratorId;

		let collaboratorElement = dom.toElement('#collaborator' + collaboratorId);

		if (collaboratorElement) {
			collaboratorElement.remove();
			this._deletedCollaborators.push(collaboratorId);
		}

	}
}

ManageCollaborators.STATE = {

	/**
	 * List of collaborators
	 * @type {Array.<Object>}
	 */
	collaborators: Config.array().required(),

	/**
	 * Path to images.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */

	spritemap: Config.string().required(),

	/**
	 * Uri to send the manage collaborators fetch request.
	 * @instance
	 * @memberof ManageCollaborators
	 * @type {String}
	 */

	uri: Config.string().required()

}

// Register component

Soy.register(ManageCollaborators, templates);

export default ManageCollaborators;
