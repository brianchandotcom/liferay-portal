(
	function(bs) {
		bs.socket.on(
			'building',
			function(data) {
				new Liferay.Notification(
					{
						delay: {
							hide: 5000,
							show: 0,
						},
						message: 'Building ' + data.fileName,
						type: 'info',
					}
				).render();
			}
		);
		bs.socket.on(
			'error',
			function(data) {
				new Liferay.Notification(
					{
						message: data.error,
						title: data.fileName,
						type: 'danger',
					}
				).render();
			}
		);
		bs.socket.on(
			'done',
			function(data) {
				new Liferay.Notification(
					{
						delay: {
							hide: 5000,
							show: 0,
						},
						message: 'Reloaded ' + data.fileName,
						type: 'success',
					}
				).render();

				if (data.fileName.endsWith('.soy')) {
					return false;
				}
				else {
					window.location.reload();
				}
			}
		);
	}
)(___browserSync___);