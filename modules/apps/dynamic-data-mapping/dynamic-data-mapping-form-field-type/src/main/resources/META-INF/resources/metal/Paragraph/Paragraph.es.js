import '../FieldBase/FieldBase.es';
import './ParagraphRegister.soy.js';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Paragraph.soy.js';

class Paragraph extends Component {
	static STATE = {

		/**
		 * @default 'string'
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		dataType: Config.string().value('string'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Paragraph
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Paragraph
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Paragraph
		 * @type {?(string|undefined)}
		 */

		label: Config.string().value(''),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Paragraph
		 * @type {?(string|undefined)}
		 */

		name: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Paragraph
		 * @type {?(string|undefined)}
		 */

		placeholder: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof Paragraph
		 * @type {?(bool|undefined)}
		 */

		required: Config.bool().value(false),

		/**
		 * @default false
		 * @instance
		 * @memberof Paragraph
		 * @type {?(bool|undefined)}
		 */

		visible: Config.bool().value(true),

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
		 * @memberof Paragraph
		 * @type {?(bool|undefined)}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Paragraph
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Paragraph
		 * @type {?(string|undefined)}
		 */

		tip: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Text
		 * @type {?(string|undefined)}
		 */

		type: Config.string().value('paragraph'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FieldBase
		 * @type {?(string|undefined)}
		 */

		tooltip: Config.string()
	};
}

Soy.register(Paragraph, templates);

export default Paragraph;