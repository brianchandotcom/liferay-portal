FROM liferay/batch:latest

#ENV LIFERAY_BATCH_CURL_OPTIONS="-v"

COPY --chown=liferay:liferay /__BATCH_TYPE__ /opt/liferay/__BATCH_TYPE__