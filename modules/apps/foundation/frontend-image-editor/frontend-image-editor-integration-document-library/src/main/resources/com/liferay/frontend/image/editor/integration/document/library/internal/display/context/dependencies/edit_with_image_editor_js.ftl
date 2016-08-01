function ${namespace}editWithImageEditor(
	editItemURL, uploadItemURL, fileEntryFilename, fileEntrySrc) {

	Liferay.Util.editEntity(
		{
			dialog: {
				destroyOnHide: true,
				zIndex: 100000
			},
			id: 'dlImageEditor',
			stack: false,
			title: '${editLanguageKey} ' + fileEntryFilename,
			uri: editItemURL,
			urlParams: {
				entityURL: fileEntrySrc,
				saveParamName: 'imageEditorFileName',
				saveURL: uploadItemURL
			}
		},
		AUI().bind('${namespace}_onSaveEditSuccess', this)
	);
}

function ${namespace}_onSaveEditSuccess() {
	Liferay.Portlet.refresh('#p_p_id${namespace}');
}