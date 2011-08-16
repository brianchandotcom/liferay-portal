Liferay.Service.register("Liferay.Service.OA", "com.liferay.portlet.osgiadmin.service", "osgi-admin-portlet");

Liferay.Service.registerClass(
	Liferay.Service.OA, "OSGi",
	{
		addBundle: true,
		getBundleContext: true,
		getState: true,
		startBundle: true,
		stopBundle: true,
		uninstallBundle: true,
		updateBundle: true
	}
);