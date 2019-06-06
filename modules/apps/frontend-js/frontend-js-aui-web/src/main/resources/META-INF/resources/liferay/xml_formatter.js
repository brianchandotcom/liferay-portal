AUI.add(
	'liferay-xml-formatter',
	function(A) {
		var Lang = A.Lang;

		var XMLFormatter = A.Component.create({
			ATTRS: {
				lineIndent: {
					validator: Lang.isString,
					value: '\r\n'
				},

				tagIndent: {
					validator: Lang.isString,
					value: '\t'
				}
			},

			EXTENDS: A.Base,

			NAME: 'liferayxmlformatter',

			prototype: {
				format: function(content) {
					var instance = this;

					var tagIndent = instance.get('tagIndent');

					var lineIndent = instance.get('lineIndent');

					return Liferay.Util.formatXML(content, {
						tagIndent,
						lineIndent
					});
				}
			}
		});

		Liferay.XMLFormatter = XMLFormatter;
	},
	'',
	{
		requires: ['aui-base']
	}
);
