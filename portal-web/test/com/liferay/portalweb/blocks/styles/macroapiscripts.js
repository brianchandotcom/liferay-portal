$(document).ready(function() {
	$(".block > .macro-name").click(function() {
		if ($(this).parent().children('.macro-info:first').is(":hidden")) {
			$(this).parent().children('.macro-info:first').slideDown("slow");
		} else {
			$(this).parent().children('.macro-info:first').slideUp();
		}
	})
});