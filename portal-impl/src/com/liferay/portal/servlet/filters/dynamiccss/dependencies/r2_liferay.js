function getGlobal() {
	return (
		function() {
			return this;
		}
	).call(null);
}

var console = {
	log: function(data) {
		java.lang.System.out.println(data);
	}
};

var global = getGlobal();

global.r2 = require('./r2.js').swap;