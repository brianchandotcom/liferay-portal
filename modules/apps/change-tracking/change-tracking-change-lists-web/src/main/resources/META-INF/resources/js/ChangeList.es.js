import Soy from 'metal-soy';
import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';
import {openToast} from 'frontend-js-web/liferay/toast/commands/OpenToast.es';

import templates from './ChangeList.soy';

class ChangeList extends PortletBase {

    created() {
        this._getDataRequest(
			this.urlProductionCollection,
			response => {
				if (response) {
					this.setState(
						{
                            description: response.description,
                            initFetch: true,
                            headerTitle: response.name,
                            publishedBy: {
                                dateTime: response.statusDate,
                                userIconUrl: '',
                                userMonogram: 'TT',
                                userName: response.statusByUserName
                                
                            }
						}
					);
				}
			}
		);
    }

    _getDataRequest(url, callback) {
		let headers = new Headers();
		headers.append('Content-Type', 'application/json');

		const request = {
			credentials: 'include',
			headers,
			method: 'GET'
        };
        
		fetch(url, request)
			.then(
				response => response.json()
			)
			.then(
				response => {
					callback(response);
				}
			)
			.catch(
				(error) => {
					const message = typeof error === 'string' ?
						error :
						Liferay.Language.get('error');

					openToast(
						{
							message,
							title: Liferay.Language.get('error'),
							type: 'danger'
						}
					);
				}
            );
	}

}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
ChangeList.STATE = {

    /**
	 * Card description
	 *
	 * @type {String}
	 */
    description: Config.string(),

    /**
	 * Card header title
	 *
	 * @type {String}
	 */
    headerTitle: Config.string(),

	/**
	 * Initial fetch happened?
	 *
	 * @type {Boolean}
	 */
    initFetch: Config.bool().value(false),

	/**
	 * Portlet namespace
	 *
	 * @type {!String}
	 */
    portletNamespace: Config.string().required(),

	/**
	 * Publised by 
	 *
	 * @type {Object}
	 */
    publisedBy: Config.shapeOf(
        {
            dateTime: Config.string(),
            userIconUrl: Config.string(),
            userMonogram: Config.string(),
            userName: Config.string()
        }
    ),

	/**
	 * Api url
	 *
	 * @type {!String}
	 */
    urlProductionCollection: Config.string().required(),

    /**
	 * Portal url
	 *
	 * @type {!String}
	 */
    urlProductionView: Config.string().required(),

    /**
	 * Path to images.
	 *
	 * @type {!String}
	 */
	spritemap: Config.string().required()

}

Soy.register(ChangeList, templates);

export {ChangeList};
export default ChangeList; 