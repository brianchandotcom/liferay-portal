/**
 * # CC0 1.0 Universal License
 *
 * Public Domain Dedication
 *
 * The person(s) who associated a work with this deed has dedicated
 * the work to the public domain by waiving all of his or her rights to
 * the work worldwide under copyright law, including all related and
 * neighboring rights, to the extent allowed by law.
 *
 * You can copy, modify, distribute and perform the work, even for
 * commercial purposes, all without asking permission.
 *
 * In no way are the patent or trademark rights of any person affected
 * by CC0, nor are the rights that other persons may have in the work or
 * in how the work is used, such as publicity or privacy rights.
 *
 * Unless expressly stated otherwise, the person(s) who associated a
 * work with this deed makes no warranties about the work, and disclaims
 * liability for all uses of the work, to the fullest extent permitted by
 * applicable law.
 *
 * When using or citing the work, you should not imply endorsement by
 * the author or the affirmer.
 *
 * This is a [human-readable summary of the Legal
 * Code](https://creativecommons.org/publicdomain/zero/1.0/) ([read the full
 * text](https://creativecommons.org/publicdomain/zero/1.0/legalcode)).
 */

/**
 * This file is based on snapshots retrieved from:
 *
 * https://raw.githubusercontent.com/jonathantneal/svg4everybody/8bd711049ba3faefb8285ca70c63a67efc35636c/LICENSE.md
 * https://raw.githubusercontent.com/jonathantneal/svg4everybody/8bd711049ba3faefb8285ca70c63a67efc35636c/lib/svg4everybody.js
 */

/*! svg4everybody v2.1.9 | github.com/jonathantneal/svg4everybody */

/* eslint-disable notice/notice */

(function () {
	function embed(parent, svg, target, use) {

		// if the target exists

		if (target) {

			// create a document fragment to hold the contents of the target

			var fragment = document.createDocumentFragment();

			// cache the closest matching viewBox

			var viewBox =
				!svg.hasAttribute('viewBox') && target.getAttribute('viewBox');

			// conditionally set the viewBox on the svg

			if (viewBox) {
				svg.setAttribute('viewBox', viewBox);
			}

			// clone the target

			var clone = document.importNode
				? document.importNode(target, true)
				: target.cloneNode(true);

			var g = document.createElementNS(
				svg.namespaceURI || 'http://www.w3.org/2000/svg',
				'g'
			);

			// copy the contents of the clone into the fragment

			while (clone.childNodes.length) {
				g.appendChild(clone.firstChild);
			}

			if (use) {
				for (var i = 0; use.attributes.length > i; i++) {
					var attr = use.attributes[i];
					if (attr.name === 'xlink:href' || attr.name === 'href') {
						continue;
					}
					g.setAttribute(attr.name, attr.value);
				}
			}

			fragment.appendChild(g);

			// append the fragment into the svg

			parent.appendChild(fragment);
		}
	}

	function loadreadystatechange(xhr, use) {

		// listen to changes in the request

		xhr.onreadystatechange = function () {

			// if the request is ready

			if (xhr.readyState === 4) {

				// get the cached html document

				var cachedDocument = xhr._cachedDocument;

				// ensure the cached html document based on the xhr response

				if (!cachedDocument) {
					cachedDocument = xhr._cachedDocument = document.implementation.createHTMLDocument(
						''
					);

					cachedDocument.body.innerHTML = xhr.responseText;

					// ensure domains are the same, otherwise we'll have issues appending the
					// element in IE 11

					if (cachedDocument.domain !== document.domain) {
						cachedDocument.domain = document.domain;
					}

					xhr._cachedTarget = {};
				}

				// clear the xhr embeds list and embed each item

				xhr._embeds.splice(0).map((item) => {

					// get the cached target

					var target = xhr._cachedTarget[item.id];

					// ensure the cached target

					if (!target) {
						target = xhr._cachedTarget[
							item.id
						] = cachedDocument.getElementById(item.id);
					}

					// embed the target into the svg

					embed(item.parent, item.svg, target, use);
				});
			}
		};

		// test the ready state change immediately

		xhr.onreadystatechange();
	}

	function svg4everybody(rawopts) {
		var opts = Object(rawopts);

		// set whether the polyfill will be activated or not

		var polyfill;
		var newerIEUA = /\bTrident\/[567]\b|\bMSIE (?:9|10)\.0\b/;
		var webkitUA = /\bAppleWebKit\/(\d+)\b/;
		var olderEdgeUA = /\bEdge\/12\.(\d+)\b/;
		var edgeUA = /\bEdge\/.(\d+)\b/;

		//Checks whether iframed

		var inIframe = window.top !== window.self;

		if ('polyfill' in opts) {
			polyfill = opts.polyfill;
		}
		else {
			polyfill =
				newerIEUA.test(navigator.userAgent) ||
				(navigator.userAgent.match(olderEdgeUA) || [])[1] < 10547 ||
				(navigator.userAgent.match(webkitUA) || [])[1] < 537 ||
				(edgeUA.test(navigator.userAgent) && inIframe);
		}

		// create xhr requests object

		var requests = {};

		// use request animation frame or a timeout to search the dom for svgs

		var requestAnimationFrame = window.requestAnimationFrame || setTimeout;

		// get a live collection of use elements on the page

		var uses = document.getElementsByTagName('use');
		var numberOfSvgUseElementsToBypass = 0;

		function oninterval() {

			// if all <use>s in the array are being bypassed, don't proceed.

			if (
				numberOfSvgUseElementsToBypass &&
				uses.length - numberOfSvgUseElementsToBypass <= 0
			) {
				return void requestAnimationFrame(oninterval, 67);
			}

			// if there are <use>s to process, proceed.

			// reset the bypass counter, since the counter will be incremented for every bypassed element,
			// even ones that were counted before.

			numberOfSvgUseElementsToBypass = 0;

			// get the cached <use> index

			var index = 0;

			// while the index exists in the live <use> collection

			while (index < uses.length) {

				// get the current <use>

				var use = uses[index];

				// get the current <svg>

				var parent = use.parentNode;
				var svg = getSVGAncestor(parent);
				var src =
					use.getAttribute('xlink:href') || use.getAttribute('href');

				if (!src && opts.attributeName) {
					src = use.getAttribute(opts.attributeName);
				}

				if (svg && src) {

					// if running with legacy support

					if (polyfill) {
						if (!opts.validate || opts.validate(src, svg, use)) {

							// remove the <use> element

							parent.removeChild(use);

							// parse the src and get the url and id

							var srcSplit = src.split('#');
							var url = srcSplit.shift();
							var id = srcSplit.join('#');

							// if the link is external

							if (url.length) {

								// get the cached xhr request

								var xhr = requests[url];

								// ensure the xhr request exists

								if (!xhr) {
									xhr = requests[url] = new XMLHttpRequest();

									xhr.open('GET', url);

									xhr.send();

									xhr._embeds = [];
								}

								// add the svg and id as an item to the xhr embeds list

								xhr._embeds.push({
									id,
									parent,
									svg,
								});

								// prepare the xhr ready state change event

								loadreadystatechange(xhr, use);
							}
							else {

								// embed the local id into the svg

								embed(
									parent,
									svg,
									document.getElementById(id),
									use
								);
							}
						}
						else {

							// increase the index when the previous value was not "valid"

							++index;
							++numberOfSvgUseElementsToBypass;
						}
					}
				}
				else {

					// increase the index when the previous value was not "valid"

					++index;
				}
			}

			// continue the interval

			requestAnimationFrame(oninterval, 67);
		}

		// conditionally start the interval if the polyfill is active

		if (polyfill) {
			oninterval();
		}
	}

	function getSVGAncestor(node) {
		var svg = node;
		while (svg.nodeName.toLowerCase() !== 'svg') {
			svg = svg.parentNode;
			if (!svg) {
				break;
			}
		}

		return svg;
	}

	window.svg4everybody = svg4everybody;
})();
