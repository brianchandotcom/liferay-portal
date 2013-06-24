CKEDITOR.dialog.add( 'liferayvideo', 
	function (editor) {
		var lang = editor.lang.liferayvideo;
	
		function commitValue(videoNode, extraStyles, videos) {		
			var value=this.getValue();
			
			if (!value && this.id=='id') {
				value = generateId();
			}
	
			if (this.id == 'poster') {		     			        
			    	videoNode.setAttribute('data-document-url',value);	
	
			        var urlChar = '?';
			        if (value.indexOf("?") >=0) {
			          urlChar = '&';
			        }
			        
			        videos[0]={};      
			        videos[0]['type']='video/mp4';
			        videos[0]['src']=value + urlChar + 'videoPreview=1&type=mp4';            
			        
			        videos[1]={};        
			        videos[1]['type']='video/ogg';
			        videos[1]['src']=value + urlChar + 'videoPreview=1&type=ogv';        		        
				
			        value = value + urlChar + 'videoThumbnail=1';
	
					videoNode.setAttribute('data-poster',value);		
					
								
					var videoDivNode =  videoNode.getChild(0);
					var divId = videoDivNode.getAttribute('id');
					var scriptNode = videoNode.getChild(1);
		
					var scriptText = 'AUI().use('+
								  	'	\'aui-base\',\'aui-video\','+
									'  	function(A) {'+
									'	   	 new A.Video('+
									'		    {'+
									'	       		boundingBox: \'#'+divId +'\','+
									'        		ogvUrl: \''+videos[1]['src']+'\','+
									'        		url: \''+ videos[0]['src']+'\','+
									'				poster: \''+value+'\','+
									'				{height}'+
									'				{width}'+
									'	     	}'+
								    	'	).render();'+
									'	}'+
									');';
		
					scriptNode.setText(scriptText);		
			} 		
	
			if (!value) {
				return;
			}
	
			switch(this.id) {
				case 'poster':
					extraStyles.backgroundImage = 'url(' + value + ')';				
					break;
				case 'width':
					extraStyles.width = value + 'px';	
					videoNode.setAttribute('data-width',value);	
	
					var scriptNode = videoNode.getChild(1);		
					if (scriptNode && scriptNode.getText()){
						var textReplace = 'height:'+value; 					
						if (scriptNode.getText().indexOf('width:')>=0)	{
								textReplace = ','+textReplace;
						}								
						scriptNode.setText(scriptNode.getText().replace(/\{height\}/g, textReplace));
					}		
					break;
				case 'height':
					extraStyles.height = value + 'px';
					videoNode.setAttribute('data-height',value);
					
					var scriptNode = videoNode.getChild(1);		
					if (scriptNode && scriptNode.getText()){
						var textReplace = 'width:'+value; 					
						if (scriptNode.getText().indexOf('height:')>=0)	{
								textReplace = ','+textReplace;
						}					
						scriptNode.setText(scriptNode.getText().replace(/\{width\}/g, textReplace));
					}				
					break;
			}
		}	
	
		function loadValue( videoNode ) {
			if (videoNode){
				
				switch (this.id) {
					case 'id':
						this.setValue( videoNode.getChild(0).getAttribute('id'));
						break;
						
					case 'poster':
						this.setValue( videoNode.getAttribute('data-document-url'));
						break;
	
					case 'height':
						this.setValue( videoNode.getAttribute('data-height'));
						break;
	
					case 'width':
						this.setValue( videoNode.getAttribute('data-width'));
						break;	
				}
				
			}
			else {
				if (this.id == 'id') {
					this.setValue( generateId() );
				}
			}
		}	
	
		function generateId() {
			var now = new Date();
			return 'video' + now.getFullYear() + now.getMonth() + now.getDate() + now.getHours() + now.getMinutes() + now.getSeconds();
		}	
	
		return {
			title : lang.dialogTitle,
			minWidth : 400,
			minHeight : 200,
	
			onShow : function() {			
				this.fakeImage = this.videoNode = null;
	
				var fakeImage = this.getSelectedElement();
	
				if (fakeImage && fakeImage.data( 'cke-real-element-type' ) && 
					fakeImage.data( 'cke-real-element-type' ) == 'liferayvideo') {
					
						this.fakeImage = fakeImage;	
						var videoNode = editor.restoreRealElement( fakeImage );					
						this.videoNode = videoNode;					
						this.setupContent( videoNode);
				}
				else {
					this.setupContent( null);
				}
			},
	
			onOk : function() {						
				var tmpid = generateId();
				
				var divNode = editor.document.createElement( 'div' );
				divNode.setAttribute('class','liferayckevideo video-container');
				
				var boundingBoxTmp = editor.document.createElement('div');
				boundingBoxTmp.setAttribute('id',tmpid);
				
				var scriptTmp = editor.document.createElement('script');
				scriptTmp.setAttribute('type','text/javascript');
				
				divNode.append(boundingBoxTmp);
				divNode.append(scriptTmp);
	
				var extraStyles = {}, videos = [];
				this.commitContent( divNode, extraStyles, videos);	
				
				var newFakeImage = editor.createFakeElement(divNode, 'liferay_cke_video', 'liferayvideo', false);
				newFakeImage.setStyles( extraStyles );
				
				if (this.fakeImage) {
					newFakeImage.replace( this.fakeImage );
					editor.getSelection().selectElement( newFakeImage );
				}
				else {		
					editor.insertElement(newFakeImage);
				}
			},		
	
			contents :
			[
				{
					id : 'info',
					elements :
					[
						{
							type : 'hbox',
							widths: [ '', '100px'],
							children : [
								{
									type : 'text',
									id : 'poster',
									label : lang.poster,
									commit : commitValue,
									setup : loadValue,								
								},
								{
									type : 'button',
									id : 'browse',
									hidden : 'true',
									style : 'display:inline-block;margin-top:10px;',
									filebrowser :
									{
										action : 'Browse',
										target: 'info:poster',
										url: editor.config.filebrowserBrowseUrl + '&Type=Video'
									},
									label : editor.lang.common.browseServer
								}]
						},
						{
							type : 'hbox',
							widths: [ '33%', '33%', '33%'],
							children : [
								{
									type : 'text',
									id : 'width',
									label : editor.lang.common.width,
									'default' : 400,
									validate : CKEDITOR.dialog.validate.notEmpty( lang.widthRequired ),
									commit : commitValue,
									setup : loadValue
								},
								{
									type : 'text',
									id : 'height',
									label : editor.lang.common.height,
									validate : CKEDITOR.dialog.validate.notEmpty( lang.heightRequired ),
									'default' : 300,
									commit : commitValue,
									setup : loadValue
								},
								{
									type : 'text',
									id : 'id',
									label : 'Id',
									commit : commitValue,
									setup : loadValue
								}]
						}
					]
				}
	
			]
		};
	});
