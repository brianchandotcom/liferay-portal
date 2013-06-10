def isAppInstalled(appName):
	appNames = AdminApp.list()

	if len(appNames) > 0:
		for curAppName in appNames.split('\n'):
			curAppName = curAppName.rstrip()

			if curAppName.find(appName) >= 0:
				return 1

	return 0

appManager = AdminControl.queryNames('${auto.deploy.websphere.wsadmin.app.manager.query}')

if isAppInstalled('${auto.deploy.websphere.wsadmin.app.name}'):
	print AdminControl.invoke(appManager, 'stopApplication', '${auto.deploy.websphere.wsadmin.app.name}')

	print AdminApp.update('${auto.deploy.websphere.wsadmin.app.name}', 'app', '[-contents ${auto.deploy.dest.dir}/${plugin.servlet.context.name}.war -contextroot /${plugin.servlet.context.name} -operation update -usedefaultbindings]')
else:
	print AdminApp.install('${auto.deploy.dest.dir}/${plugin.servlet.context.name}.war', '[-appname ${auto.deploy.websphere.wsadmin.app.name} -contextroot /${plugin.servlet.context.name} -usedefaultbindings]')

print AdminConfig.save()

print AdminControl.invoke(appManager, 'startApplication', '${auto.deploy.websphere.wsadmin.app.name}')