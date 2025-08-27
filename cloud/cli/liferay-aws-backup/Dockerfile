ARG APP_NAME=liferay-aws-backup

FROM golang:1.24 AS builder

ARG APP_NAME

WORKDIR /workspace

COPY resources .

RUN go mod download && \
	go mod verify && \
	CGO_ENABLED=0 go build -a -ldflags="-s -w" -o ${APP_NAME} main.go

FROM gcr.io/distroless/static:nonroot

ARG APP_NAME

ENTRYPOINT ["/${APP_NAME}"]

COPY --from=builder /workspace/${APP_NAME} /

USER 65532:65532