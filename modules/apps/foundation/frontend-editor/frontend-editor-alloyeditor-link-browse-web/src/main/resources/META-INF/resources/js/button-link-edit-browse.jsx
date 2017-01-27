/* global React, ReactDOM AlloyEditor */

(function () {
    'use strict';

    var React = AlloyEditor.React;

	var ButtonLinkEditBrowse = React.createClass({
        // Lifecycle. Provides static properties to the widget.
        statics: {
            /**
             * The name which will be used as an alias of the button in the configuration.
             *
             * @static
             * @property {String} key
             * @default linkEdit
             */
            key: 'linkEditBrowse'
        },

    	render() {
            return (
                <div>
                    <AlloyEditor.ButtonLinkEdit {...this.props} />
                    <button aria-label="Browse" className="ae-button" onClick={this._browseClick} title="browse">
                        Browse
                    </button>
                </div>
            );
    	},

        _browseClick: function(event) {
            this.props.editor.fire('browseLinkClick');
        }
    });

    AlloyEditor.Buttons[ButtonLinkEditBrowse.key] = AlloyEditor.ButtonLinkEditBrowse = ButtonLinkEditBrowse;
}());