@spira_Sites @Sites @team_FARO @priority_3
Feature: Assert Sites Activities 180 Days Time Period Filter In Page Table

  Background: [Setup] Navigate to Sites page
    * I go to the "Home" page
    * I login as "test@faro.io:test"
    * I should see the "Sites" page

  Scenario: 180 Days Time Period Filter In Page Table
    Given I go to the "Site_Pages" page
    When I click the "Last 30 days" button
    And I click the "More Preset Periods" button
    And I click the "Last 180 days" button
    Then I should see 10 rows in the table