@spira_Assets @Assets @team_FARO @priority_5
Feature: Assert Assets Forms Custom Range Time Filter in Behaviour Report

  Background: [Setup] Navigate to the main page
    * I go to the "Home" page
    * I login as "test@faro.io:test"
    * I should see the "Sites" page

  Scenario: Custom Range Time Filter in Assets > Forms Behaviour Report
    Given I go to the "Assets" page
    And I click the "Forms" tab
    And I click "extend virtual channels" in the table
    When I click the "Last 30 days" dropdown in the card
    And I click the "Custom Range" button
    And I set start date "August 1 2020" and end date "August 8 2020"
    Then I should see time filter "Aug 1, 2020 - Aug 8, 2020" in card