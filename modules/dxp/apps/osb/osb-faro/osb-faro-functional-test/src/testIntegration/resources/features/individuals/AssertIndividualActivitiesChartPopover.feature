@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Assert Individual Activities Chart popover follows format
  As a Business User, I should be able to see Individual Activity popover follows format

  Background: [Setup] Navigate to Individuals page
      * I go to the "Home" page
      * I login as "test@faro.io:test"
      * I should see the "Sites" page
      * I go to the "Individuals" page

  Scenario: Individual Activity chart popover
      Given I click the "Known Individuals" tab
      And I click "Abram Bauch" in the table
      When I click the "Last 30 days" dropdown in the "Individual Activities" card
      And I click the "Custom Range" button
      And I set start date "July 1 2020" and end date "July 13 2020"
      When I mouse over row "1" in "Individual Activities" card
      Then I should see chart popover formatted YYYY MMM DD