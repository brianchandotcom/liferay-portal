Liferay.Service.register("Liferay.Service.OAuth", "com.liferay.oauth.service", "oauth-portlet");

Liferay.Service.registerClass(
	Liferay.Service.OAuth, "OAuthApplication",
	{
		addOAuthApplication: true,
		deleteLogo: true,
		deleteOAuthApplication: true,
		updateOAuthApplication: true
	}
);

Liferay.Service.registerClass(
	Liferay.Service.OAuth, "OAuthUser",
	{
		deleteOAuthUser: true
	}
);