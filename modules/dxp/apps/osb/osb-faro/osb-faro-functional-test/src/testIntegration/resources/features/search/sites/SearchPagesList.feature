@spira_Search @Search @Sites @List @team_FARO @priority_5
  Feature: Search for a Page in Pages list
    As an Business User, I should be able to Search for a Page in the Pages List

    Background: [Setup]
        * I go to the "Home" page
        * I login as "test@faro.io:test"
        * I should see the "Sites" page
        * I click "Sites" in the sidebar

    Scenario: Search for a Page
        Given I click the "Pages" tab
        When I search for "recontextualize value-added channels"
        Then I should only see Pages named "recontextualize value-added channels" in the table