const fetchLicense = async (
	accountKey,
	dateEnd,
	dateStart,
	environment,
	licenseKeyDownloadURL,
	productName,
	sessionId
) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(
		`${licenseKeyDownloadURL}/accounts/${accountKey}/product-groups/${productName}/product-environment/${environment}/common-license-key?dateEnd=${dateEnd}&dateStart=${dateStart}`,
		{
			headers: {
				'Okta-Session-ID': sessionId,
			},
		}
	);

	return response;
};

export {fetchLicense};
