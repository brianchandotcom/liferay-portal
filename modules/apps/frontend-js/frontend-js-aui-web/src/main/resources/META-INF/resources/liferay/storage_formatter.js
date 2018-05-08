AUI.add(
	'liferay-storage-formatter',
	function(A) {
		var Lang = A.Lang;

		var STR_SPACE = ' ';

		var STR_SUFFIX_KB = 'suffixKB';

		var StorageFormatter = function() {
		};

		StorageFormatter.NAME = 'storageformatter';

		StorageFormatter.ATTRS = {
			addSpaceBeforeSuffix: {
				validator: Lang.isBoolean,
				value: false
			},

			decimalSeparator: {
				validator: Lang.isString,
				value: '.'
			},

			denominator: {
				validator: Lang.isNumber,
				value: 1024.0
			},

			suffixGB: {
				validator: Lang.isString,
				value: 'GB'
			},

			suffixKB: {
				validator: Lang.isString,
				value: 'KB'
			},

			suffixMB: {
				validator: Lang.isString,
				value: 'MB'
			}
		};

		StorageFormatter.prototype = {
			formatStorage: function(size) {
				var instance = this;

				var suffix = instance.get(STR_SUFFIX_KB);

				var denominator = instance.get('denominator');

				size /= denominator;

				if (size >= denominator) {
					suffix = instance.get('suffixMB');

					size /= denominator;
				}

				if (size >= denominator) {
					suffix = instance.get('suffixGB');

					size /= denominator;
				}

				return A.Number.format(
					size,
					{
						decimalPlaces: instance._getDecimalPlaces(size, suffix),
						decimalSeparator: instance.get('decimalSeparator'),
						suffix: instance.get('addSpaceBeforeSuffix') ? STR_SPACE + suffix : suffix
					}
				);
			},

			_getDecimalPlaces: function(size, suffix) {
				var instance = this;

				var decimalPlaces = 1;

				var suffixKB = instance.get(STR_SUFFIX_KB);

				if (suffix === suffixKB) {
					decimalPlaces = 0;
				}

				return decimalPlaces;
			}
		};

		Liferay.StorageFormatter = StorageFormatter;
	},
	'',
	{
		requires: ['aui-base', 'datatype-number-format']
	}
);