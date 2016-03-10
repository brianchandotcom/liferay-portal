window.Liferay = window.Liferay || {};

Liferay.Widget = function(options) {
	options = options || {};

	var id = options.id || '_Liferay_widget' + (Math.ceil(Math.random() * (new Date()).getTime()));
	var height = options.height || '100%';
	var url = options.url || 'http://www.liferay.com/widget/web/guest/community/forums/-/message_boards';
	var width = options.width || '100%';

	var html = '<iframe frameborder="0" height="' + height + '" id="' + id + '" src="' + url + '" width="' + width + '"></iframe>';

	document.write(html);
};