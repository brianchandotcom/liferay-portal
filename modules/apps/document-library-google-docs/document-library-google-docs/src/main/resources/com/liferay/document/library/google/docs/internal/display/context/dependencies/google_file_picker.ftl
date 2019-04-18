function GoogleFilePicker(callback) {
	if (window.gapi) {
		callback();
	}
	else {
		Liferay.once('googleAPILoaded', callback);
	}
}

GoogleFilePicker.prototype = {
	constructor: GoogleFilePicker,

	openPicker: function() {
		var instance = this;

		instance._createPicker();

		if (!instance._authAPILoaded) {
			gapi.load(
				'auth',
				{
					'callback': instance._onAuthAPILoad.bind(instance)
				}
			);
		}

		if (!instance._pickerAPILoaded) {
			gapi.load(
				'picker',
				{
					'callback': instance._onPickerAPILoad.bind(instance)
				}
			);
		}
	},

	_createPicker: function() {
		var instance = this;

		if (instance._pickerAPILoaded && instance._authAPILoaded) {
			var viewId = google.picker.ViewId;

			var groupDocuments = new google.picker.ViewGroup(viewId.DOCS);

			groupDocuments.addView(viewId.DOCUMENTS);
			groupDocuments.addView(viewId.SPREADSHEETS);
			groupDocuments.addView(viewId.PRESENTATIONS);

			var groupPhotos = new google.picker.ViewGroup(viewId.PHOTOS);

			groupPhotos.addView(viewId.PHOTO_UPLOAD);
			groupPhotos.addView(viewId.WEBCAM);

			var picker = new google.picker.PickerBuilder();

			picker.addViewGroup(groupDocuments);
			picker.addViewGroup(groupPhotos);

			picker.addView(viewId.RECENTLY_PICKED);

			picker.setOAuthToken(instance._oauthToken);
			picker.setDeveloperKey(GoogleFilePicker.API_KEY);
			picker.setCallback(instance._pickerCallback);

			picker.build().setVisible(true);
		}
	},

	_onAuthAPILoad: function() {
		var instance = this;

		window.gapi.auth.authorize(
			{
				'client_id': GoogleFilePicker.CLIENT_ID,
				'immediate': false,
				'scope': GoogleFilePicker.SCOPE
			},
			function(authResult) {
				if (authResult && !authResult.error) {
					instance._oauthToken = authResult.access_token;

					instance._authAPILoaded = true;

					instance._createPicker();
				}
			}
		);
	},

	_onPickerAPILoad: function() {
		var instance = this;

		instance._pickerAPILoaded = true;

		instance._createPicker();
	},

	_pickerCallback: function(data) {
		if (data[google.picker.Response.ACTION] === google.picker.Action.PICKED) {
			var doc = data[google.picker.Response.DOCUMENTS][0];

			var googlePickerDoc = google.picker.Document;

			${onFilePickCallback}(
				{
					"DESCRIPTION": doc[googlePickerDoc.DESCRIPTION] || '',
					"EMBEDDABLE_URL": doc[googlePickerDoc.EMBEDDABLE_URL] || '',
					"ICON_URL": doc[googlePickerDoc.ICON_URL] || '',
					"ID": doc[googlePickerDoc.ID],
					"NAME": doc[googlePickerDoc.NAME],
					"URL": doc[googlePickerDoc.URL] || ''
				});
		}
	}
};

GoogleFilePicker.API_KEY = '${htmlUtil.escapeJS(googleAppsAPIKey)}';

GoogleFilePicker.CLIENT_ID = '${htmlUtil.escapeJS(googleClientId)}';

GoogleFilePicker.SCOPE = [
	'https://www.googleapis.com/auth/drive.readonly',
	'https://www.googleapis.com/auth/photos.upload'
];

window.onGoogleAPILoad = function() {
	Liferay.fire('googleAPILoaded');
};

if (!window.gapi && !document.getElementById('googleAPILoader')) {
	var scriptNode = document.createElement('script');

	scriptNode.id = 'googleAPILoader';
	scriptNode.src = 'https://apis.google.com/js/api.js?onload=onGoogleAPILoad';

	document.body.appendChild(scriptNode);
}
else if (window.gapi) {
	Liferay.fire('googleAPILoaded');
}

var FilePicker = GoogleFilePicker;