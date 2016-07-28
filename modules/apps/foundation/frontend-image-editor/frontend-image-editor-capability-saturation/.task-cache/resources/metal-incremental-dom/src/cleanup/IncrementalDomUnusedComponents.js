define(['exports', 'metal/src/metal'], function (exports, _metal) {
	'use strict';

	Object.defineProperty(exports, "__esModule", {
		value: true
	});

	function _classCallCheck(instance, Constructor) {
		if (!(instance instanceof Constructor)) {
			throw new TypeError("Cannot call a class as a function");
		}
	}

	var IncrementalDomUnusedComponents = function () {
		function IncrementalDomUnusedComponents() {
			_classCallCheck(this, IncrementalDomUnusedComponents);
		}

		IncrementalDomUnusedComponents.schedule = function schedule(comps) {
			for (var i = 0; i < comps.length; i++) {
				comps[i].getRenderer().parent_ = null;
				comps_.push(comps[i]);
			}
			if (!scheduled_) {
				scheduled_ = true;
				_metal.async.nextTick(disposeUnused_);
			}
		};

		return IncrementalDomUnusedComponents;
	}();

	var comps_ = [];
	var scheduled_ = false;

	/**
  * Disposes all sub components that were not rerendered since the last
  * time this function was scheduled.
  * @protected
  */
	function disposeUnused_() {
		for (var i = 0; i < comps_.length; i++) {
			if (!comps_[i].isDisposed()) {
				var renderer = comps_[i].getRenderer();
				if (!renderer.getParent()) {
					// Don't let disposing cause the element to be removed, since it may
					// be currently being reused by another component.
					comps_[i].element = null;
					renderer.getOwner().disposeSubComponents([comps_[i].config.ref]);
				}
			}
		}
		scheduled_ = false;
		comps_ = [];
	}

	exports.default = IncrementalDomUnusedComponents;
});
//# sourceMappingURL=IncrementalDomUnusedComponents.js.map