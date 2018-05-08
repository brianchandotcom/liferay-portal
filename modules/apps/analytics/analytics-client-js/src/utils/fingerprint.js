/**
 * Gets details from the installed browser plugins
 * @return {string} A string representation of the browser plugins
 */
function getBrowserPluginDetails() {
	return Array.from(navigator.plugins)
		.map(plugin => plugin.name)
		.reduce((plugins, plugin) => `${plugins},${plugin}`, '');
}

/**
 * Executes graphic operations over a canvas to get a representation of the
 * environment graphic capabilities
 * @return {string} A string represenation of the canvas graphic capabilities
 */
function getCanvasFingerprint() {
	let canvasFingerprint = '';

	try {
		const canvas = document.createElement('canvas');
		const context = canvas.getContext('2d');
		const fingerPrintText = 'analytics-client-js, <canvas> 1.0';

		// Execute canvas context operations and export the result
		context.textBaseline = 'top';
		context.font = "14px 'Arial'";
		context.textBaseline = 'alphabetic';
		context.fillStyle = '#f60';
		context.fillRect(125, 1, 62, 20);
		context.fillStyle = '#069';
		context.fillText(fingerPrintText, 2, 15);
		context.fillStyle = 'rgba(102, 204, 0, 0.7)';
		context.fillText(fingerPrintText, 4, 17);

		canvasFingerprint = canvas.toDataURL();
	} catch (error) {}

	return canvasFingerprint;
}

/**
 * Generates a fingerprint based on the current user capabilities
 *
 * browserPluginDetails: comma-separated list of the names of the different plugins installed in the browser.
 * canvasFingerPrint: a specific canvas fingerprint based on graphics environment
 * language: selected language in the browser
 * platform: native OS platform
 * timeZone: selected timezone
 * userAgent: browser exposed user agent
 *
 * Other accepted values are:
 * cookiesEnabled
 * dataSourceIdentifier
 * dataSourceIndividualIdentifier
 * domain
 * emailAddress
 * httpAcceptHeaders
 * screenSizeAndColorDepth
 * systemFonts
 * temporaryUserID
 * touchSupport
 * webGLFingerPrint
 * @return {object} A fingerprint object
 */
function fingerprint() {
	const browserPluginDetails = getBrowserPluginDetails();
	const canvasFingerPrint = getCanvasFingerprint();
	const language = navigator.language;
	const platform = navigator.platform;
	const timezone = String(String(new Date()).split('(')[1]).split(')')[0];
	const userAgent = navigator.userAgent;

	return {
		browserPluginDetails,
		canvasFingerPrint,
		language,
		platform,
		timezone,
		userAgent,
	};
}

export {fingerprint};
export default fingerprint;