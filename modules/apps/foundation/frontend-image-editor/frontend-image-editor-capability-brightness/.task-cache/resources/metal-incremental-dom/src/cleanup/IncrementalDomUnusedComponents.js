define(['exports'], function (exports) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var comps_ = [];

	var IncrementalDomUnusedComponents = function () {
		function IncrementalDomUnusedComponents() {
			_classCallCheck(this, IncrementalDomUnusedComponents);
		}

		IncrementalDomUnusedComponents.disposeUnused = function disposeUnused() {
			for (var i = 0; i < comps_.length; i++) {
				if (!comps_[i].isDisposed()) {
					var renderer = comps_[i].getRenderer();
					if (!renderer.getParent()) {
						// Don't let disposing cause the element to be removed, since it may
						// be currently being reused by another component.
						comps_[i].element = null;

						var ref = comps_[i].config.ref;
						var owner = renderer.getOwner();
						if (owner.components[ref] === comps_[i]) {
							owner.disposeSubComponents([ref]);
						} else {
							comps_[i].dispose();
						}
					}
				}
			}
			comps_ = [];
		};

		IncrementalDomUnusedComponents.schedule = function schedule(comps) {
			for (var i = 0; i < comps.length; i++) {
				comps[i].getRenderer().parent_ = null;
				comps_.push(comps[i]);
			}
		};

		return IncrementalDomUnusedComponents;
	}();

	exports.default = IncrementalDomUnusedComponents;
});
//# sourceMappingURL=IncrementalDomUnusedComponents.js.map