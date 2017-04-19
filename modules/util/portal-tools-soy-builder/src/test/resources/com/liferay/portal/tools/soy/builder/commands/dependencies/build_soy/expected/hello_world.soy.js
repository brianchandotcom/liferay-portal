// This file was automatically generated from hello_world.soy.
// Please don't edit this file by hand.

/**
 * @fileoverview Templates in namespace samples.
 * @public
 */

if (typeof samples == 'undefined') { var samples = {}; }


samples.helloWorld = function(opt_data, opt_ignored) {
  return soydata.VERY_UNSAFE.ordainSanitizedHtml('Hello world!');
};
if (goog.DEBUG) {
  samples.helloWorld.soyTemplateName = 'samples.helloWorld';
}
