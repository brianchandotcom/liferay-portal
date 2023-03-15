@spira_Order @Order @Accounts @List @team_FARO @priority_3
Feature: Order an Account's Associated Individuals
	As a Business User, I should be able order different columns of an Account's Associated Individuals list

	Background: [Setup] Sync a Salesforce Data Source
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Accounts" page
		* I search for "Lesch, Walsh and Stracke"
		* I click "Lesch, Walsh and Stracke" in the table
		* I click the "Individuals" tab

	Scenario: Order Associated Individuals List

		# Order by name/email

		When I click the "Order" button
		And I click the "Name" dropdown option
		Then I should see the following ordered rows in column 1:
			| Abram Bauch      |
			| Carey Anderson   |
			| Daniela Jacobson |
			| Dannie Armstrong |
			| Jeramy VonRueden |

		# Order by title

		When I click the "Title" dropdown option
		Then I should see the following ordered rows in column 2:
			| Chief Response Orchestrator          |
			| District Branding Associate          |
			| Internal Quality Liaison             |
			| International Quality Representative |
			| Investor Metrics Representative      |