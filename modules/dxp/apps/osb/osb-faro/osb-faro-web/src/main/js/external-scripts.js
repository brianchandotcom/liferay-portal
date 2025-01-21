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

let scripts = [
	/* Google Tag Manager */
	{
		innerHTML:
			"(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0], j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src='https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f); })(window,document,'script','dataLayer','GTM-NHH7QQ7');"
	}
];

if (FARO_ENV === 'prd') {
	scripts = [
		...scripts,
		{
			innerHTML: `(function(apiKey){
			(function(p,e,n,d,o){var v,w,x,y,z;o=p[d]=p[d]||{};o._q=o._q||[];
				v=['initialize','identify','updateOptions','pageLoad','track'];for(w=0,x=v.length;w<x;++w)(function(m){
					o[m]=o[m]||function(){o._q[m===v[0]?'unshift':'push']([m].concat([].slice.call(arguments,0)));};})(v[w]);
				y=e.createElement(n);y.async=!0;y.src='https://cdn.pendo.io/agent/static/'+apiKey+'/pendo.js';
				z=e.getElementsByTagName(n)[0];z.parentNode.insertBefore(y,z);})(window,document,'script','pendo');
			})('${FARO_PENDO_API_KEY}')`
		}
	];
} else {
	scripts = [
		...scripts,
		{
			innerHTML:
				'(function(){window.pendo = {identify: () => {}, initialize: () => {}, isReady: () => {}}})()'
		}
	];
}

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
