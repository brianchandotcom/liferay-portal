FROM golang:1.24 AS builder

WORKDIR /workspace

COPY go.mod go.mod
COPY go.sum go.sum

RUN go mod download && \
	go mod verify

COPY internal/ internal/
COPY main.go main.go

RUN CGO_ENABLED=0 \
	go build main.go \
	-a \
	-ldflags="-s -w" \
	-o manager

FROM gcr.io/distroless/static:nonroot

WORKDIR /

COPY --from=builder /workspace/manager .

USER 65532:65532

ENTRYPOINT ["/manager"]
