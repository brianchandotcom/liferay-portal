def isAppInstalled(appName):
	appNames = AdminApp.list(${auto.deploy.websphere.wsadmin.app.manager.list.options})

	if len(appNames) > 0:
		for curAppName in appNames.split('\n'):
			curAppName = curAppName.rstrip()

			if curAppName.find(appName) >= 0:
				return 1

	return 0

appManager = AdminControl.queryNames('${auto.deploy.websphere.wsadmin.app.manager.query}')

if isAppInstalled('${auto.deploy.websphere.wsadmin.app.name}'):
	print AdminControl.invoke(appManager, 'stopApplication', '${auto.deploy.websphere.wsadmin.app.name}')

	print AdminApp.update('${auto.deploy.websphere.wsadmin.app.name}', 'app', '[${auto.deploy.websphere.wsadmin.app.manager.update.options}]')
else:
	print AdminApp.install('${auto.deploy.dest.dir}/${plugin.servlet.context.name}.war', '[${auto.deploy.websphere.wsadmin.app.manager.install.options}]')

print AdminConfig.save()

print AdminControl.invoke(appManager, 'startApplication', '${auto.deploy.websphere.wsadmin.app.name}')