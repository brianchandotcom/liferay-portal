# Testray

## Google Cloud Platform Key

1. You need a JSON file with your service account credentials.
2. Save your JSON file containing your credentials in `/site-initializer-testray/extra/java-function/src/main/resources`.

## Application Properties
Edit ***application.properties***:
   1. Example:
   ```
   testray.base.url=http://localhost:8080/o/c/
   testray.bucket.name=testray-results
   testray.password=test
   testray.url.api.key=/key.json
   testray.user=test@liferay.com
   ```

## Run

1. Start Liferay.
2. Go to ***/site-initializer-testray/extra/java-function***.
3. ```gw run```