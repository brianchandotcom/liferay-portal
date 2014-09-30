var googleClientId = '${googleClientId}';

var googleAppsAPIKey = '${googleAppsAPIKey}';

var scope = [
	'https://www.googleapis.com/auth/drive.readonly',
	'https://www.googleapis.com/auth/photos.upload'
];

var authAPILoaded = false;
var googleAPILoaded = false;
var pickerAPILoaded = false;

var oauthToken;
var openPickerOnGAPILoaded = false;

Liferay.provide(
	window,
	'onGoogleAPILoad',
	function() {
		Liferay.fire('googleAPILoad');
	},
	['aui-base']
);

Liferay.on(
	'googleAPILoad',
	function() {
	    googleAPILoaded = true;

	    if (openPickerOnGAPILoaded) {
		    openPicker();
	    }
	}
);

function ${namespace}openPicker() {
	if (googleAPILoaded) {
	    gapi.load(
		    'auth',
		    {
		        'callback': onAuthAPILoad
		    }
		);
		gapi.load(
		    'picker',
		    {
		        'callback': onPickerAPILoad
		    }
		);
	}
	else {
	    openPickerOnGAPILoaded = true;
	}
};

var onAuthAPILoad = function() {
	window.gapi.auth.authorize(
	    {
		    'client_id': googleClientId,
		    'immediate': false,
		    'scope': scope
	    },
	    function(authResult) {
		    if (authResult && !authResult.error) {
			    oauthToken = authResult.access_token;

			    authAPILoaded = true;

			    createPicker();
		    }
	    }
	);
};

var onPickerAPILoad = function() {
	pickerAPILoaded = true;

	createPicker();
};

var createPicker = function() {
	if (pickerAPILoaded && authAPILoaded) {
	    var ViewId = google.picker.ViewId;

	    var groupDocuments = new google.picker.ViewGroup(ViewId.DOCS);

	    groupDocuments.addView(ViewId.DOCUMENTS);
	    groupDocuments.addView(ViewId.SPREADSHEETS);
	    groupDocuments.addView(ViewId.PRESENTATIONS);

	    var groupPhotos = new google.picker.ViewGroup(ViewId.PHOTOS);

	    groupPhotos.addView(ViewId.PHOTO_UPLOAD);
	    groupPhotos.addView(ViewId.WEBCAM);

	    var picker = new google.picker.PickerBuilder();

	    picker.addViewGroup(groupDocuments);
	    picker.addViewGroup(groupPhotos);

	    picker.addView(ViewId.RECENTLY_PICKED);

	    picker.setOAuthToken(oauthToken);
	    picker.setDeveloperKey(googleAppsAPIKey);
	    picker.setCallback(pickerCallback);

	    picker.build().setVisible(true);
	}
};

var pickerCallback = function(data) {
	if (data[google.picker.Response.ACTION] === google.picker.Action.PICKED) {
	    var doc = data[google.picker.Response.DOCUMENTS][0];

	    var googlePickerDoc = google.picker.Document;

	    var documentId = doc[googlePickerDoc.ID];
	    var documentName = doc[googlePickerDoc.NAME];
	    var documentDescription = doc[googlePickerDoc.DESCRIPTION] || '';
	    var documentIconURL = doc[googlePickerDoc.ICON_URL] || '';
	    var documentViewURL = doc[googlePickerDoc.EMBEDDABLE_URL] || '';
	    var documentEditURL = doc[googlePickerDoc.URL] || '';

		${onFilePickCallback}({
			"ID": documentId,
			"Title": documentName,
			"Description": documentDescription,
			"Icon": documentIconURL,
			"Embed_URL": documentViewURL,
			"Edit_URL": documentEditURL
	    });
	}
};

if (!window.gapi && !document.getElementById('googleAPILoader')) {
	var scriptNode = document.createElement('script');

	scriptNode.id = 'googleAPILoader';
	scriptNode.src = 'https://apis.google.com/js/api.js?onload=onGoogleAPILoad';

	document.body.appendChild(scriptNode);
}
else if (window.gapi) {
	Liferay.fire('googleAPILoad');
}