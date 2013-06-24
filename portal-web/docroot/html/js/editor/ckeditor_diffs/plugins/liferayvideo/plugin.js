/*	 
	 * Liferay Video plugin for CKEditor.
	 * Based on Video plugin for CKEditor Copyright (C) 2011 Alfonso Martínez de
	 * Lizarrondo
	 *  == BEGIN LICENSE ==
	 * 
	 * Licensed under the terms of any of the following licenses at your choice:
	 *  - GNU General Public License Version 2 or later (the "GPL")
	 * http://www.gnu.org/licenses/gpl.html
	 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
	 * http://www.gnu.org/licenses/lgpl.html
	 *  - Mozilla Public License Version 1.1 or later (the "MPL")
	 * http://www.mozilla.org/MPL/MPL-1.1.html
	 *  == END LICENSE ==
	 * 
	 */

( function() {

CKEDITOR.plugins.add( 'liferayvideo',{
	
	lang : [ 'en', 'es' ],

	getPlaceholderCss : function()
	{
		return 'img.cke_video' +
				'{' +
					'background-image: url(' + CKEDITOR.getUrl( this.path + 'icons/placeholder.png' ) + ');' +
					'background-position: center center;' +
					'background-repeat: no-repeat;' +
					'background-color:gray;'+
					'border: 1px solid #a9a9a9;' +
					'width: 80px;' +
					'height: 80px;' +
				'}';
	},

	onLoad : function()
	{
		// v4
		if (CKEDITOR.addCss)
			CKEDITOR.addCss( this.getPlaceholderCss() );

	},

	init : function( editor )
	{
		var lang = editor.lang.liferayvideo;		

		CKEDITOR.dialog.add( 'liferayvideo', this.path + 'dialogs/video.js' );
		
		editor.addCommand( 'LiferayVideo', new CKEDITOR.dialogCommand( 'liferayvideo'));

		editor.ui.addButton( 'LiferayVideo',
			{
				label : lang.toolbar,
				command : 'LiferayVideo',
				icon : this.path + 'icons/icon.png'
			} );		

		if ( editor.addMenuItems )
		{
			editor.addMenuItems(
				{
					liferayvideo :
					{
						label : lang.properties,
						command : 'LiferayVideo',
						group : 'flash'
					}
				});
		}

		editor.on( 'doubleclick', function( evt )
			{
				var element = evt.data.element;

				if ( element.is( 'img' ) && element.data( 'cke-real-element-type' ) == 'liferayvideo' )
					evt.data.dialog = 'liferayvideo';
			});
		
		if ( editor.contextMenu )
		{
			editor.contextMenu.addListener( function( element, selection )
				{
					if ( element && element.is( 'img' ) && !element.isReadOnly()
							&& element.data( 'cke-real-element-type' ) == 'liferayvideo' )
						return { video : CKEDITOR.TRISTATE_OFF };
				});
		}
		
		editor.lang.fakeobjects.liferayvideo = lang.fakeObject;


	}, 

	afterInit: function( editor )
	{
		var dataProcessor = editor.dataProcessor,
			htmlFilter = dataProcessor && dataProcessor.htmlFilter,
			dataFilter = dataProcessor && dataProcessor.dataFilter;
		
		dataFilter.addRules(
			{

			elements : {
					'div' : function( realElement )
					{						
						if (realElement.attributes["class"] && realElement.attributes["class"].indexOf( "liferayckevideo" ) >= 0)
						{	
							var fakeElement = editor.createFakeParserElement( realElement, 'liferay_cke_video', 'liferayvideo', false ),
								fakeStyle = fakeElement.attributes.style || '';

							var width = realElement.attributes['data-width'],
								height = realElement.attributes['data-height'],
								poster = realElement.attributes['data-poster'];

							if ( typeof width != 'undefined' )
								fakeStyle = fakeElement.attributes.style = fakeStyle + 'width:' + CKEDITOR.tools.cssLength( width ) + ';';

							if ( typeof height != 'undefined' )
								fakeStyle = fakeElement.attributes.style = fakeStyle + 'height:' + CKEDITOR.tools.cssLength( height ) + ';';

							if ( poster )
								fakeStyle = fakeElement.attributes.style = fakeStyle + 'background-image:url(' + poster + ');';

							return fakeElement;
						}
					}
				}

			}
		);
		
	}

} ); 

})();
