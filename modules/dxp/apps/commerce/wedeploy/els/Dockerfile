FROM docker.elastic.co/elasticsearch/elasticsearch:6.1.4

RUN /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-icu && \
  /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-kuromoji && \
  /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-smartcn && \
  /usr/share/elasticsearch/bin/elasticsearch-plugin install --batch analysis-stempel

COPY --chown=elasticsearch:elasticsearch elasticsearch.yml /usr/share/elasticsearch/config/
