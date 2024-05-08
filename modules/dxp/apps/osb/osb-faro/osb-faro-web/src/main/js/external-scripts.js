/**
 * A list of external scripts to be appended to the page. Each script
 * can also specify the attributes it needs. For example, the zendesk
 * widget requires that its script tag has a certain id attribute.
 *
 * Also, note that webpack will actually evaluate the boolean expressions
 * below at build time and remove any cases that can never be reached
 * (dead-code elim). This means we don't have to worry about the development
 * scripts being present in our production bundle. To keep this working, make
 * sure that we only do comparisons to string or number literals.
 */

const scripts = [
	/* Google Tag Manager */
	{
		innerHTML:
			"(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0], j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src='https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f); })(window,document,'script','dataLayer','GTM-NHH7QQ7');"
	}
];

/**
 * Runtime logic for adding external scripts to the page.
 */
function appendScript(options) {
	const script = document.createElement('script');

	if (options.src) {
		script.async = true;
	}

	for (const [name, value] of Object.entries(options)) {
		script[name] = value;
	}

	document.body.appendChild(script);
}

scripts.filter(({innerHTML, src}) => src || innerHTML).forEach(appendScript);
