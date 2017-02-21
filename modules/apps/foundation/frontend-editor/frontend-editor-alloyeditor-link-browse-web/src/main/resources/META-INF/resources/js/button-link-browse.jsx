/* global React, ReactDOM AlloyEditor */

(function () {
    'use strict';

    var React = AlloyEditor.React;

    /**
     * The ButtonLinkBrowse class provides functionality for creating and editing a link in a document,
     * and also allows to link to an existing file in DM.
     * ButtonLinkBrowse renders in two different modes:
     *
     * - Normal: Just a button that allows to switch to the edition mode
     * - Exclusive: The ButtonLinkEditBrowse UI with all the link edition controls.
     *
     * @uses ButtonKeystroke
     * @uses ButtonStateClasses
     * @uses ButtonCfgProps
     *
     * @class ButtonLinkBrowse
     */
	var ButtonLinkBrowse = React.createClass({
        mixins: [AlloyEditor.ButtonKeystroke, AlloyEditor.ButtonStateClasses, AlloyEditor.ButtonCfgProps],

        // Allows validating props being passed to the component.
        propTypes: {
            /**
             * The editor instance where the component is being used.
             *
             * @property {Object} editor
             */
            editor: React.PropTypes.object.isRequired,

            /**
             * The label that should be used for accessibility purposes.
             *
             * @property {String} label
             */
            label: React.PropTypes.string,

            /**
             * The tabIndex of the button in its toolbar current state. A value other than -1
             * means that the button has focus and is the active element.
             *
             * @property {Number} tabIndex
             */
            tabIndex: React.PropTypes.number
        },

        // Lifecycle. Provides static properties to the widget.
        statics: {
            key: 'linkBrowse'
        },

        /**
         * Lifecycle. Returns the default values of the properties used in the widget.
         *
         * @method getDefaultProps
         * @return {Object} The default properties.
         */
        getDefaultProps: function() {
            return {
                keystroke: {
                    fn: '_requestExclusive',
                    keys: CKEDITOR.CTRL + 76 /*L*/
                }
            };
        },

        /**
         * Lifecycle. Renders the UI of the button.
         *
         * @method render
         * @return {Object} The content which should be rendered.
         */
    	render() {
            if (this.props.renderExclusive) {
                var props = this.mergeButtonCfgProps();

                return (
                    <AlloyEditor.ButtonLinkEditBrowse {...this.props} />
                );
            }
            else {
                return (
                    <AlloyEditor.ButtonLink {...this.props} />
                )
            }
    	},

        /**
         * Requests the link button to be rendered in exclusive mode to allow the creation of a link.
         *
         * @protected
         * @method _requestExclusive
         */
        _requestExclusive: function() {
            this.props.requestExclusive(ButtonLinkBrowse.key);
        }
    });

    AlloyEditor.Buttons[ButtonLinkBrowse.key] = AlloyEditor.ButtonLinkBrowse = ButtonLinkBrowse;
}());