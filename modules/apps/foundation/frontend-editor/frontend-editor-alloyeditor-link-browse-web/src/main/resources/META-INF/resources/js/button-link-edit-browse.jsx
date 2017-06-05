/* global React, ReactDOM AlloyEditor */

(function () {
	'use strict';

	var React = AlloyEditor.React;

	/**
	 * The FolderIconSvg class renders a folder icon.
	 *
	 * @class FolderIconSvg
	 */
	var FolderIconSvg = React.createClass({
		/**
         * Lifecycle. Renders the UI of the button.
         *
         * @method render
         * @return {Object} The content which should be rendered.
         */
		render: function() {
			return (
				<svg width="19px" height="16px" viewBox="0 0 19 16" version="1.1">
				    <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd">
				        <g id="folder_icon" fill-rule="nonzero" fill="#FFFFFF">
				            <path d="M1.16154142e-20,3.2 L7.82504921e-20,13.8932647 C8.51581999e-20,15.0017792 0.890925393,15.9004077 1.99742191,15.9004077 L17.0025781,15.9004077 C18.1057238,15.9004077 19,15.0109745 19,13.8961809 L19,5.25591756 C19,4.14901366 18.1131578,3.24410791 16.9941243,3.2350902 C16.9941243,3.2350902 13.9000228,3.20635475 11.1841707,3.2 L5.55111512e-16,3.2 Z M0.00476820776,1.92 C0.0763137892,0.875650807 0.944078934,0.0525431571 2.00404495,0.0525431571 L6.37206813,0.0525431571 C7.69758986,0.0525431571 8.07530451,1.04873135 8.52730884,1.92 L0.00476820776,1.92 Z"></path>
				        </g>
				    </g>
				</svg>
			)
		}
	});

 	/**
     * The ButtonLinkEditBrowse class provides functionality for creating and editing a link in a document,
     * and also allows to link to an existing file in DM.
     * Provides UI for creating, editing and removing a link.
     *
     * @uses WidgetDropdown
     * @uses WidgetFocusManager
     * @uses ButtonCfgProps
     *
     * @class ButtonLinkEditBrowse
     */
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

        /**
         * Lifecycle. Renders the UI of the button.
         *
         * @method render
         * @return {Object} The content which should be rendered.
         */
		render: function() {
			return (
				<div>
					<AlloyEditor.ButtonLinkEdit ref='linkEditButton' {...this.props} />
					<button aria-label="Browse" className="ae-button" onClick={this._browseClick} title="browse">
						<FolderIconSvg />
					</button>
				</div>
			);
		},

		/**
         * Opens an item selector dialog.
         *
         * @protected
         * @method _browseClick
         */
		_browseClick: function() {
			var instance = this;

			var editor = this.props.editor.get('nativeEditor');

			var url = editor.config.documentBrowseLinkUrl;

			var linkTarget = this.refs.linkEditButton.state.linkTarget;

			AUI().use('liferay-item-selector-dialog', (A) => {
				var itemSelectorDialog = new A.LiferayItemSelectorDialog(
					{
						eventName: editor.name + 'selectDocument',
						on: {
							selectedItemChange: function(event) {
								var selectedItem = event.newVal;

								if (selectedItem) {
						            instance._updateLink(selectedItem.value, linkTarget, selectedItem.title);
								}
							}
						},
						title: Liferay.Language.get('select-item'),
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
         * @param {String} linkHref href value for the link
         * @param {String} linkTarget target value for the link
         * @param {String} linkTitle if the link is a title that points to a wiki page (only works for creole)
         */
        _updateLink: function(linkHref, linkTarget, linkTitle) {
            var editor = this.props.editor.get('nativeEditor');
            var linkUtils = new CKEDITOR.Link(editor, {appendProtocol: false});
            var linkAttrs = {
                target: linkTarget
            };
            var modifySelection = { advance: true };

            if (linkHref) {
            	if (editor.plugins && editor.plugins.creole && !linkTitle) {
            		linkHref = location.origin + linkHref;
            	}

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