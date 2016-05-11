function ${namespace}editWithImageEditor(
	editItemUrl, uploadItemUrl, fileEntryFilename, fileEntrySrc) {

	Liferay.Util.editEntity(
		{
			dialog: {
				destroyOnHide: true,
				zIndex: 100000
			},
			id: 'dlImageEditor',
			stack: false,
			title: 'Edit ' + fileEntryFilename,
			uri: editItemUrl,
			urlParams: {
				entityURL: fileEntrySrc,
				saveParamName: 'imageEditorFileName',
				saveURL: uploadItemUrl
			}
		},
		AUI().bind('${namespace}_onSaveEditSuccess', this)
	);
}

function ${namespace}_onSaveEditSuccess() {
	Liferay.Portlet.refresh('#p_p_id${namespace}');
}