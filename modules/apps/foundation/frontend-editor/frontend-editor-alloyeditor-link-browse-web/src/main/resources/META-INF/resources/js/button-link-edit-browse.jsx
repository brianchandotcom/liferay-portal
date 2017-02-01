/* global React, ReactDOM AlloyEditor */

(function () {
	'use strict';

	var React = AlloyEditor.React;

	var ButtonLinkEditBrowse = React.createClass({
		// Allows validating props being passed to the component.
        propTypes: {
        	/**
             * The editor instance where the component is being used.
             *
             * @property {Object} editor
             */
            editor: React.PropTypes.object.isRequired
        },
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

		componentWillUnmount: function() {
			console.log('unmount');
		},


		/**
         * Lifecycle. Invoked once before the component is mounted.
         * The return value will be used as the initial value of this.state.
         *
         * @method getInitialState
         */
        getInitialState: function() {
            var link = new CKEDITOR.Link(this.props.editor.get('nativeEditor')).getFromSelection();
            var href = link ? link.getAttribute('href') : '';

            return {
                element: link,
                linkHref: href
            };
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
			var editor = this.props.editor.get('nativeEditor');

			var url = editor.config.documentBrowseLinkUrl;

			var instance = this;

			AUI().use('liferay-item-selector-dialog', (A) => {
				var itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						on: {
							selectedItemChange: function(event) {
								var selectedItem = event.newVal;

								if (selectedItem) {
						            instance._updateLink(selectedItem.value);
								}
							}
						},
						eventName: editor.name + 'selectDocument',
						url: url
					}
				);

				itemSelectorDialog.open();
			});
		},

		/**
         * Updates the link in the editor element. If the element didn't exist previously, it will
         * create a new <a> element with the href specified in the link input.
         *
         * @protected
         * @method _updateLink
         */
        _updateLink: function(linkHref) {
            var editor = this.props.editor.get('nativeEditor');
            var linkUtils = new CKEDITOR.Link(editor, {appendProtocol: false});
            var linkAttrs = {
                target: '_blank'
            };
            var modifySelection = { advance: true };

            if (linkHref) {
                if (this.state.element) {
                    linkAttrs.href = linkHref;

                    linkUtils.update(linkAttrs, this.state.element, modifySelection);
                } else {
                    linkUtils.create(linkHref, linkAttrs, modifySelection);
                }

                editor.fire('actionPerformed', this);
            }
        }
	});

	AlloyEditor.Buttons[ButtonLinkEditBrowse.key] = AlloyEditor.ButtonLinkEditBrowse = ButtonLinkEditBrowse;
}());