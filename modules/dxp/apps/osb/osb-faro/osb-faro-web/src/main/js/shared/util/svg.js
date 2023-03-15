import {keyBy} from 'lodash';

function reqSVG(req) {
	return req.keys().map((svg, id) => req(svg, id).default);
}

const clayCssReq = require.context(
	'@clayui/css/src/images/icons',
	false,
	/\.svg$/
);
const faroReq = require.context('../../../images', false, /\.svg$/);

const svgs = keyBy(reqSVG(clayCssReq).concat(reqSVG(faroReq)), 'id');

/**
 * The properties of a loaded SVG.
 * @typedef {Object} SVGProperties
 * @property {string!} id - The id of the SVG, which is useful for the
 * "use" tag of the Svg element.
 * @property {string!} viewBox - The viewbox string of the svg element.
 */

/**
 * The properties of the default SVG.
 * @constant
 * @type {SVGProperties}
 */
const DEFAULT_SVG = {
	id: 'question-circle',
	viewBox: '0 0 512 512'
};

/**
 * Returns an object with properties that describe the symbol. If
 * no SVG is found that matches the symbol, then a fallback SVG is returned.
 * @param {string} symbol - The symbol for the SVG.
 * @returns {SVGProperties}
 */
export default function getSVG(symbol) {
	return svgs[`${symbol}-usage`] || DEFAULT_SVG;
}
