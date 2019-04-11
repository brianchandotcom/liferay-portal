function ${namespace}open_${dialogId}(uri, title, refreshOnClose) {
	Liferay.Util.openWindow(
		{
			dialog: {
				centered: true,
				constrain: true,
				cssClass: 'sharing-dialog',
				destroyOnHide: true,
				modal: true,
				height: 540,
				width: 600,
				on: {
					visibleChange: function(event) {
						if (refreshOnClose && !event.newVal) {
							Liferay.Portlet.refresh('#p_p_id${namespace}');
						}
					}
				}
			},
			id: '${namespace}${dialogId}',
			title: title,
			uri: uri
		}
	);
}