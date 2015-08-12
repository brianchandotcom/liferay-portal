AUI.add(
	'liferay-crop-region',
	function(A) {
		var Lang = A.Lang;

		var CropRegion = function() {};

		CropRegion.prototype = {
			_getCropRegion: function(imagePreview, region) {
				var instance = this;

				var naturalSize = instance._getImgNaturalSize(imagePreview);

				var scaleY = naturalSize.height / imagePreview.height();
				var scaleX = naturalSize.width / imagePreview.width();

				var regionHeight = region.height ? (region.height * scaleY) : naturalSize.height;
				var regionWidth = region.width ? (region.width * scaleX) : naturalSize.width;

				var regionX = region.x ? Math.max(region.x * scaleX, 0) : 0;
				var regionY = region.y ? Math.max(region.y * scaleY, 0) : 0;

				return {
					height: regionHeight,
					width: regionWidth,
					x: regionX,
					y: regionY
				};
			},

			_getImgNaturalSize: function(img) {
				var instance = this;

				var imageHeight = img.get('naturalHeight');
				var imageWidth = img.get('naturalWidth');

				if (Lang.isUndefined(imageHeight) || Lang.isUndefined(imageWidth)) {
					var tmp = new Image();

					tmp.src = img.attr('src');

					imageHeight = tmp.height;
					imageWidth = tmp.width;
				}

				return {
					height: imageHeight,
					width: imageWidth
				};
			}
		};

		Liferay.CropRegion = CropRegion;
	},
	'',
	{
		requires: ['aui-base']
	}
);