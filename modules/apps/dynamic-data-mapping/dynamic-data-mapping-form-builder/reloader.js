;(
	function(bs) {
		var resetModules = function(moduleName) {
			Object.keys(Liferay.MODULES).forEach(
				function(currentModule) {
					var moduleCache = Liferay.MODULES[currentModule];

					if (moduleCache.name.indexOf('.soy') > -1) {
						return;
					}

					if (moduleCache.name.indexOf(moduleName) > -1) {
						delete moduleCache.implementation;
						delete moduleCache.pendingImplementation;

						moduleCache.requested = false;
					}
				}
			);
		}
		bs.socket.on(
			'building',
			function(data) {
				new Liferay.Notification(
					{
						delay: {
							hide: 5000,
							show: 0
						},
						message: 'Building ' + data.fileName,
						type: 'info'
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
						type: 'danger'
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
							show: 0
						},
						message: 'Reloaded ' + data.fileName,
						type: 'success'
					}
				).render();

				if (data.fileName.endsWith('.soy')) {
					return false;
				}
				else if (data.fileName.endsWith('.soy.js')) {
					window.location.reload();
				}
				else {
					resetModules(data.moduleName);

					if (Liferay.Forms.App) {
						Liferay.Forms.App.reset();
					}
				}
			}
		);
	}
)(___browserSync___);