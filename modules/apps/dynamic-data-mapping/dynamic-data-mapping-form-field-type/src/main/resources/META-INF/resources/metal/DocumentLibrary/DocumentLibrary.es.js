import '../FieldBase/FieldBase.es';
import './DocumentLibraryRegister.soy.js';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './DocumentLibrary.soy.js';

class DocumentLibrary extends Component {
	static STATE = {

		/**
		 * @default 'string'
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		dataType: Config.string().value('date'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(string|undefined)}
		 */

		name: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(string|undefined)}
		 */

		placeholder: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(false),

		/**
		 * @default false
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(bool|undefined)}
		 */

		required: Config.bool().value(false),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(bool|undefined)}
		 */

		repeatable: Config.bool(),

		/**
		 * @default true
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(bool|undefined)}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		strings: {
			value: {
				select: Liferay.Language.get('select')
			}
		},

		/**
		 * @default undefined
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(string|undefined)}
		 */

		tip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		tooltip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		type: Config.string().value('document_library'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof DocumentLibrary
		 * @type {?(string|undefined)}
		 */

		value: Config.object()
	};
}

Soy.register(DocumentLibrary, templates);

export default DocumentLibrary;