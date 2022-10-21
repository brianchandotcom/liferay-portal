FROM alpine:latest

COPY rootCA.pem .
COPY job.sh /src/job.sh
COPY *.data.batch-engine.json /src

RUN \
	apk add --no-cache bash curl jq tree && \
	chmod +x /src/job.sh

WORKDIR src

ENTRYPOINT ["/src/job.sh"]