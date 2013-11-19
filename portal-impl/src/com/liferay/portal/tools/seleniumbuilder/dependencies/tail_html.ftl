<script>
	if (document.getElementsByClassName("testFail")){
		var classes = document.getElementsByClassName("testFail");
		var ids = classes[0].getAttribute("id");
		var status = document.getElementsByClassName("status");
		status[ids - 1].className = "fail";
	}
</script>