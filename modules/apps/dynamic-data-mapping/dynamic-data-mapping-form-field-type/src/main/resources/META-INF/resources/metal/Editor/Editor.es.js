import '../FieldBase/index.es';
import './EditorRegister.soy.js';
import {Config} from 'metal-state';
import Component from 'metal-component';
import Soy from 'metal-soy';
import templates from './Editor.soy.js';

class Editor extends Component {
	static STATE = {

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
		 * @type {?(string|undefined)}
		 */

		editorValue: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
		 * @type {?(string|undefined)}
		 */

		fieldName: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
		 * @type {?(string|undefined)}
		 */

		id: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
		 * @type {?(string|undefined)}
		 */

		label: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
		 * @type {?(string|undefined)}
		 */

		name: Config.string().required(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
		 * @type {?(string|undefined)}
		 */

		placeholder: Config.string(),

		/**
		 * @default false
		 * @instance
		 * @memberof Editor
		 * @type {?bool}
		 */

		readOnly: Config.bool().value(false),

		/**
		 * @default false
		 * @instance
		 * @memberof Editor
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
		 * @memberof Editor
		 * @type {?(bool|undefined)}
		 */

		showLabel: Config.bool().value(true),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
		 * @type {?(string|undefined)}
		 */

		spritemap: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
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

		type: Config.string().value('editor'),

		/**
		 * @default undefined
		 * @instance
		 * @memberof Editor
		 * @type {?(string|undefined)}
		 */

		value: Config.string().value('')
	};

	created() {
		AUI().use(
			'liferay-alloy-editor',
			A => {
				this.A = A;
			}
		);
	}

	attached() {
		this._createEditor();
	}

	willReceiveState({children, value}) {
		if (value && value.newVal !== value.prevVal && children) {
			this._alloyEditor.getNativeEditor().setData(value.newVal);
		}
	}

	_createEditor() {
		const {A, name, readOnly, value} = this;
		const editorNode = this.element.querySelector('.alloy-editor');

		if (readOnly) {
			return;
		}

		editorNode.innerHTML = value;

		window[name] = {};

		this._alloyEditor = new A.LiferayAlloyEditor(
			{
				contents: value,
				editorConfig: {
					extraPlugins: 'ae_placeholder,ae_selectionregion,ae_uicore',
					removePlugins: 'contextmenu,elementspath,image,link,liststyle,resize,tabletools,toolbar',
					srcNode: A.one(editorNode),
					toolbars: {
						add: {
							buttons: ['hline', 'table']
						},
						styles: {
							selections: AlloyEditor.Selections,
							tabIndex: 1
						}
					}
				},
				namespace: name,
				onChangeMethod: this._onChangeEditor.bind(this),
				plugins: [],
				textMode: false
			}
		).render();

		this._alloyEditor.getNativeEditor().on('actionPerformed', this._onActionPerformed.bind(this));
	}

	_onActionPerformed(e) {
		const {
			data: {
				props
			}
		} = e;

		if (!props.command) {
			this._onChangeEditor(e);
		}
	}

	_onChangeEditor(event) {
		const value = this._alloyEditor.getHTML();

		this.setState(
			{
				value
			},
			() => this.emit(
				'fieldEdited',
				{
					fieldInstance: this,
					originalEvent: event,
					value
				}
			)
		);
	}
}

Soy.register(Editor, templates);

export default Editor;