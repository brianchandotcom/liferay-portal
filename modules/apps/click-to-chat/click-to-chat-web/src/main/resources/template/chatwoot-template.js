<!-- Start of Chatwoot Chat Code -->
<script>
  (function(d,t) {
    var BASE_URL="https://app.chatwoot.com";
    var g=d.createElement(t),s=d.getElementsByTagName(t)[0];
    g.src=BASE_URL+"/packs/js/sdk.js";
    s.parentNode.insertBefore(g,s);
    g.onload=function(){
      window.chatwootSDK.run({
        websiteToken: '%s',
        baseUrl: BASE_URL
      })
    }
  })(document,"script");
</script>
<!-- End of Chatwoot Chat Code -->

<!-- Start of Chatwoot User Identification Code -->
<script>
    window.onload = function(){
      window.$chatwoot.setUser('%s', {
        email: '%s',
        name: '%s'
      });
    }
</script>
<!-- End of Chatwoot User Identification Code -->