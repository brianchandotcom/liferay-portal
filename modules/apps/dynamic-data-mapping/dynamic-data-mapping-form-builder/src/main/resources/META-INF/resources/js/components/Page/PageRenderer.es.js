import './PaginatedPageRenderer.soy.js';
import './SimplePageRenderer.soy.js';
import './TabbedPageRenderer.soy.js';
import './WizardPageRenderer.soy.js';
import 'clay-button';
import 'clay-dropdown';
import 'clay-modal';
import * as FormSupport from '../Form/FormSupport.es';
import Component from 'metal-component';
import core from 'metal';
import Soy from 'metal-soy';
import templates from './PageRenderer.soy.js';
import {Config} from 'metal-state';
import {pageStructure} from '../../util/config.es';

class PageRenderer extends Component {
	getPage(page) {
		const {editingLanguageId} = this;

		if (core.isObject(page.description)) {
			page = {
				...page,
				description: page.description[editingLanguageId]
			};
		}
		if (core.isObject(page.title)) {
			page = {
				...page,
				title: page.title[editingLanguageId]
			};
		}

		return page;
	}

	isEmptyPage({rows}) {
		let empty = false;

		if (!rows || !rows.length) {
			empty = true;
		} else {
			empty = !rows.some(({columns}) => {
				let hasFields = true;

				if (!columns) {
					hasFields = false;
				} else {
					hasFields = columns.some(column => column.fields.length);
				}
				return hasFields;
			});
		}
		return empty;
	}

	prepareStateForRender(states) {
		return {
			...states,
			empty: this.isEmptyPage(states.page),
			page: this.getPage(states.page)
		};
	}

	_handleFieldBlurred(event) {
		this.emit('fieldBlurred', event);
	}

	_handleFieldClicked({delegateTarget}) {
		const fieldNode = delegateTarget.parentElement.parentElement;
		const indexes = FormSupport.getIndexes(fieldNode);

		this.emit('fieldClicked', {
			...indexes
		});
	}

	_handleFieldEdited(event) {
		this.emit('fieldEdited', event);
	}
}

PageRenderer.STATE = {
	/**
	 * @instance
	 * @memberof FormPage
	 * @type {?number}
	 */

	activePage: Config.number().value(0),

	/**
	 * @instance
	 * @memberof FormPage
	 * @type {?boolean}
	 */
	editable: Config.bool().value(false),

	/**
	 * @default []
	 * @instance
	 * @memberof FormRenderer
	 * @type {?array<object>}
	 */

	page: pageStructure,

	/**
	 * @default 1
	 * @instance
	 * @memberof FormPage
	 * @type {?number}
	 */

	pageIndex: Config.number().value(0),

	/**
	 * @default undefined
	 * @instance
	 * @memberof FormRenderer
	 * @type {!string}
	 */

	spritemap: Config.string().required()
};

Soy.register(PageRenderer, templates);

export default PageRenderer;
